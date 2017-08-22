package pl.nask.crs.app.permissions;

import org.testng.annotations.Test;

import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface NicHandleTestMethods {

    void getNH();
    void getNotOwnNH();
    void historyNH();
    void historyNotOwnNH();
    void alterStatusNH();
    void alterStatusNotOwnNH();
    void saveNH();
    void saveNotOwnNH();
    void saveNewPassword();
    void saveNewPasswordNotOwn();
    void changePassword();
    void changePasswordNotOwn();
    void resetPassword();
    void resetPasswordNotOwn();
    void createNH();
    void getDefaults();
    void getDefaultsNotOwn();
    void saveDefaults();
    void saveDefaultsNotOwn();
    void removeUserPermission();
    void addUserPermission();
    void changeTfaOwn();
    void changeTfaNotOwn();
    void changeVATCategory();
    void changeCountryAffectsVATCategory();
    
}
