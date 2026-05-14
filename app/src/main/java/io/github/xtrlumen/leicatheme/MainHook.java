package io.github.xtrlumen.leicatheme;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public static final String TAG = "LeicaTheme ";
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        String packageName = lpparam.packageName;
        switch (packageName) {
            case "com.android.thememanager":
                XposedBridge.log("D/" + TAG + "Loaded into " + packageName);
                try {
                    XposedHelpers.findAndHookMethod(
                        "android.os.SystemProperties",
                        lpparam.classLoader,
                        "get",
                        String.class,
                        String.class,
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) {
                                if ("ro.boot.product.theme_customize".equals(param.args[0])) {
                                    param.setResult("P1_Leica");
                                    XposedBridge.log("D/" + TAG + "ro.boot.product.theme_customize -> P1_Leica");
                                }
                            }
                        }
                    );
                } catch (Throwable e) {
                    XposedBridge.log("E/" + TAG + "'android.os.SystemProperties.get' Module Hook failed: " + e.getMessage());
                }
                XposedBridge.log("D/" + TAG + "Hooked");
                break;
            default:
                XposedBridge.log("D/" + TAG + "Ignored " + packageName);
                break;
        }
    }
}