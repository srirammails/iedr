package pl.nask.crs.web.nichandles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.nask.crs.app.authorization.PermissionAppService;
import pl.nask.crs.app.authorization.queries.SimpleChangeLevelPermissionQuery;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Marianna Mysiorska
 */
public class NicHandleAccessLevelAction extends AuthenticatedUserAwareAction {

    public static final String INPUT = "input";
    public static final String EDIT_GROUPS = "editgroups";
    public static final String EDIT_PERMS= "editperms";
    private static final String PERMISSION_NAME = "pl.nask.crs.app.users.UserAppService.setPermissionGroups";
    private static final int LIMIT = 15;

    private final NicHandleAppService nicHandleAppService;
    private final UserAppService userAppService;
    private AuthorizationGroupsFactory authorizationGroupsFactory;
    private PermissionAppService permissionService;
    private NicHandle nicHandle;
    private NicHandleWrapper wrapper;
    private String nicHandleId;
    private String changeFrom;
    private String changeTo;    
    private List<Group> groups;
    private Map<String, Group> groupsMap = new HashMap<String, Group>();
    private TableParams tableParams = new TableParams();
    TicketsPaginatedList<HistoricalObject<User>> paginatedHistory;
    private long changeId;
    private int historicalSelected = -1;
    private int selectedPage;
    
    private String permissionName;

    
    public NicHandleAccessLevelAction(NicHandleAppService nicHandleAppService, UserAppService userAppService, AuthorizationGroupsFactory authorizationGroupsFactory, PermissionAppService permissionService) {
        Validator.assertNotNull(nicHandleAppService, "nicHandleAppService");
        Validator.assertNotNull(userAppService, "user app service");
        Validator.assertNotNull(authorizationGroupsFactory, "authorization groups factory");
        Validator.assertNotNull(permissionService, "permission service");
        this.nicHandleAppService = nicHandleAppService;
        this.userAppService = userAppService;
        this.authorizationGroupsFactory = authorizationGroupsFactory;
        this.permissionService = permissionService;        
        groups = authorizationGroupsFactory.getAllGroups();
        for (Group g : groups) {
            groupsMap.put(g.getName(), g);
        }
    }

    public NicHandleWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(NicHandleWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String input() throws Exception {
        LimitedSearchResult<HistoricalObject<User>> l = userAppService.getHistory(getUser(), getNicHandleId(), (tableParams.getPage()-1)*LIMIT, LIMIT);
        paginatedHistory = new TicketsPaginatedList<HistoricalObject<User>>(l.getResults(), (int) l.getTotalResults(), tableParams.getPage(), LIMIT);

        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        wrapper = new NicHandleWrapper(nicHandle);
        wrapper.createPermissionGroupsWrapper(authorizationGroupsFactory);        
        
        List<HistoricalObject<User>> hist = l.getResults();
        if (changeId >= 0 && hist != null && !hist.isEmpty()) {
            for (int i = 0; i < hist.size(); i++) {
                HistoricalObject<User> hObject = hist.get(i);
                if (hObject.getChangeId() == changeId) {
                    wrapper.setUser(hObject.getObject());
                    wrapper.setGroupsFromUser();
                    historicalSelected = i;
                }
            }
            setSelectedPage(tableParams.getPage());
        } else {
            User u = nicHandleAppService.getUserAppService().getUser(nicHandle.getNicHandleId());
            // if the user is null, then we'll have to create a stub
            if (u == null)
                u = new User(nicHandleId, null, null ,null, null, new HashSet<Group>(), Collections.EMPTY_MAP, true);
            wrapper.setUser(u);
            wrapper.setGroupsFromUser();
            setSelectedPage(-1);
        }
        
                
        return INPUT;
    }

    public String edit() throws Exception {
        getData();
        return INPUT;
    }
    
    public String editPermissions() throws Exception {
        getData();
        return INPUT;
    }
    
    private Group groupForName(String groupName) {
        if (Validator.isEmpty(groupName))
            return null;        
        return groupsMap.get(groupName);
    }
    
    public List<Group> changeableFrom(String groupName) {
        Group l = groupForName(groupName);
        List<Group> list = new ArrayList<Group>();        
        for (Group g : groups) {
            if (checkPermission(l, g))
                list.add(g);
        }
        return list;
    }

    public List<Group> getAllGroups() {
        return groups;
    }
    
    public List<String> getAllPermissions() {
    	return authorizationGroupsFactory.getAllPermissionNames();
    }
    
    public List<Group> getSortedGroups(Set<Group> ug) {
        List<Group> l = new ArrayList<Group>();
        for (Group g : groups) { // to maintain order
            if (ug.contains(g))
                l.add(g);
        }
        return l;
    }
    
    public List<Group> getUserGroups() {
        Set<Group> ug = getWrapper().getPermissionGroupsWrapper().getPermissionGroups();

        return getSortedGroups(ug);
    }
    
    public Set<Permission> getGroupPermissions() {
    	Set<Permission> permissions = new HashSet<Permission>();
    	for (Group g: getWrapper().getPermissionGroupsWrapper().getPermissionGroups()) {
    		permissions.addAll(g.getPermissions());
    	}
    	return permissions;
    }
    
    public Set<String> getUserPermissions() {
    	return getWrapper().getUserPermissions();
    }

    private boolean checkPermission(Group from, Group to) {
        return permissionService.hasPermission(getUser(), new SimpleChangeLevelPermissionQuery(PERMISSION_NAME, from, to));
    }

    public String save() throws Exception {
        Set<Group> newGroups = wrapper.getPermissionGroupsWrapper().getPermissionGroups();
        Set<Group> oldGroups = wrapper.getUser().getPermissionGroups();
        
        Set<Group> remove = new HashSet<Group>(oldGroups);
        remove.removeAll(newGroups);
        
        Set<Group> add = new HashSet<Group>(newGroups);
        newGroups.clear();

        userAppService.changePermissionGroups(getUser(), wrapper.getUser(), remove, add);
        nicHandleAppService.triggerNhExport(getUser(), wrapper.getUser().getUsername());
        getData();
        LimitedSearchResult<HistoricalObject<User>> l = userAppService.getHistory(getUser(), getNicHandleId(), (tableParams.getPage() - 1) * LIMIT, LIMIT);
        paginatedHistory = new TicketsPaginatedList<HistoricalObject<User>>(l.getResults(), (int) l.getTotalResults(), tableParams.getPage(), LIMIT);

        return SUCCESS;
    }

     
    public boolean isFullAccess() {
        return permissionService.hasPermission(getUser(), new NamedPermissionQuery("fullAccess"));
    }
     
    public String changePermission() throws Exception {
        getData();
        Group lfrom = groupForName(changeFrom);
        Group lto = groupForName(changeTo);
        if (lfrom == null || lto == null)
            return INPUT;
        
        Set<Group> remove = new HashSet<Group>();
        remove.add(lfrom);

        Set<Group> add = new HashSet<Group>();
        add.add(lto);
        
        userAppService.changePermissionGroups(getUser(), wrapper.getUser(), remove, add);
        nicHandleAppService.triggerNhExport(getUser(), wrapper.getUser().getUsername());
        return SUCCESS;
    }
    
    public String addPermission() throws Exception {
    	nicHandleAppService.addUserPermission(getUser(), nicHandleId, permissionName);
    	getData();
    	return SUCCESS;
    }
    
    public String removePermission() throws Exception {
    	nicHandleAppService.removeUserPermission(getUser(), nicHandleId, permissionName);
    	getData();
    	return SUCCESS;
    }
    
    private void getData() throws Exception {
        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        wrapper = new NicHandleWrapper(nicHandle);
        wrapper.createPermissionGroupsWrapper(authorizationGroupsFactory);
        User u = nicHandleAppService.getUserAppService().getUser(nicHandle.getNicHandleId());
//        if the user is null, then we'll have to create a stub
        if(u==null)
            u = new User(nicHandleId, null, null, null, null, new HashSet<Group>(), Collections.EMPTY_MAP, true);
        wrapper.setUser(u);
        wrapper.setGroupsFromUser();
    }

    public void setChangeFrom(String changeFrom) {
        this.changeFrom = changeFrom;
    }

    public void setChangeTo(String changeTo) {
        this.changeTo = changeTo;
    }

    public TableParams getTableParams() {
        return tableParams;
    }

    public void setTableParams(TableParams tableParams) {
        this.tableParams = tableParams;
    }

    public TicketsPaginatedList<HistoricalObject<User>> getPaginatedHistory() {
        return paginatedHistory;
    }
    
    public boolean isHistory() {
        return historicalSelected >= 0;
    }
    
    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public int getHistoricalSelected() {
        return historicalSelected;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
    
    public String getPermissionName() {
		return permissionName;
	}
    
}