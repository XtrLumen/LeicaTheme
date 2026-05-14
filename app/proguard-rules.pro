-keep class io.github.xtrlumen.leicatheme.MainHook
-assumenosideeffects class de.robv.android.xposed.XposedBridge {
    public static void log(...);
}

-repackageclasses
-overloadaggressively
-allowaccessmodification