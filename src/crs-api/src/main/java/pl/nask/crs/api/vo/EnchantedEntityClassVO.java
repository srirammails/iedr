package pl.nask.crs.api.vo;

import pl.nask.crs.entities.EnchancedEntityClass;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class EnchantedEntityClassVO {

    private EntityClassVO entityClass;
    private List<EntityCategoryVO> categories;

    public EnchantedEntityClassVO() {
    }

    public EnchantedEntityClassVO(EnchancedEntityClass enchancedEntityClass) {
        this.entityClass = new EntityClassVO(enchancedEntityClass.getEntityClass());
        this.categories = toVOList(enchancedEntityClass.getCategories());
    }

    private List<EntityCategoryVO> toVOList(List<EntityCategory> entityCategoryList) {
        if (entityCategoryList == null || entityCategoryList.size() == 0)
            return new ArrayList<EntityCategoryVO>(0);
        List<EntityCategoryVO> ret = new ArrayList<EntityCategoryVO>();
        for (EntityCategory entityCategory : entityCategoryList) {
            ret.add(new EntityCategoryVO(entityCategory));
        }
        return ret;
    }

    public EntityClassVO getEntityClass() {
        return entityClass;
    }

    public List<EntityCategoryVO> getCategories() {
        return categories;
    }
}
