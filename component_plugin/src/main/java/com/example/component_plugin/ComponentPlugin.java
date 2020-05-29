package com.example.component_plugin;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.example.component_base.ComponentConst;

/**
 * Author: ryan.lei
 * Date: 2020/5/29 10:27
 * Description:
 */
public class ComponentPlugin implements IComponent {
    @Override
    public String getName() {
        return ComponentConst.Component_plugin.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        //此处的action是外部调用该组件内部的方法的标记，用于区分该组件内不同的功能或者方法，
        //由于component_b依赖了base组件，所以可以直接引用base组件里的常量
        String action = cc.getActionName();
        switch (action) {
            case ComponentConst.Component_plugin.Action.SHOW_ACTIVITY:
                CCUtil.navigateTo(cc, PluginActivity.class);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                break;
            default:
                break;
        }
        return true;
    }
}
