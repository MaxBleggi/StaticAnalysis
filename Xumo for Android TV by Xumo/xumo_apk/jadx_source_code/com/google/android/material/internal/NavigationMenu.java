package com.google.android.material.internal;

import android.content.Context;
import android.view.SubMenu;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

@RestrictTo({Scope.LIBRARY_GROUP})
public class NavigationMenu extends MenuBuilder {
    public NavigationMenu(Context context) {
        super(context);
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) addInternal(i, i2, i3, charSequence);
        i2 = new NavigationSubMenu(getContext(), this, menuItemImpl);
        menuItemImpl.setSubMenu(i2);
        return i2;
    }
}
