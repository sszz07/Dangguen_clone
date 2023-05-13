<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="mtrl_calendar_pre_l_text_clip_padding">0dp</dimen>
    <dimen name="mtrl_exposed_dropdown_menu_popup_vertical_offset">1dp</dimen>
    <item format="float" name="mtrl_high_ripple_default_alpha" type="dimen">0.48</item>
    <item format="float" name="mtrl_high_ripple_focused_alpha" type="dimen">0.48</item>
    <item format="float" name="mtrl_high_ripple_hovered_alpha" type="dimen">0.16</item>
    <item format="float" name="mtrl_high_ripple_pressed_alpha" type="dimen">0.48</item>
    <item format="float" name="mtrl_low_ripple_default_alpha" type="dimen">0.24</item>
    <item format="float" name="mtrl_low_ripple_focused_alpha" type="dimen">0.24</item>
    <item format="float" name="mtrl_low_ripple_hovered_alpha" type="dimen">0.08</item>
    <item format="float" name="mtrl_low_ripple_pressed_alpha" type="dimen">0.24</item>
    <style name="Base.Theme.MaterialComponents" parent="Base.V21.Theme.MaterialComponents"/>
    <style name="Base.Theme.MaterialComponents.Dialog" parent="Base.V21.Theme.MaterialComponents.Dialog"/>
    <style name="Base.Theme.MaterialComponents.Light" parent="Base.V21.Theme.MaterialComponents.Light"/>
    <style name="Base.Theme.MaterialComponents.Light.Dialog" parent="Base.V21.Theme.MaterialComponents.Light.Dialog"/>
    <style name="Base.ThemeOverlay.MaterialComponents.Dialog" parent="Base.V14.ThemeOverlay.MaterialComponents.Dialog">
    <item name="android:windowBackground">@drawable/mtrl_dialog_background</item>
  </style>
    <style name="Base.ThemeOverlay.MaterialComponents.Dialog.Alert.Framework" parent="@android:style/Theme.Material.Dialog.Alert">
    <item name="android:buttonBarButtonStyle">@style/Widget.MaterialComponents.Button.TextButton.Dialog.Flush</item>
  </style>
    <style name="Base.ThemeOverlay.MaterialComponents.Light.Dialog.Alert.Framework" parent="@android:style/Theme.Material.Light.Dialog.Alert">
    <item name="android:buttonBarButtonStyle">@style/Widget.MaterialComponents.Button.TextButton.Dialog.Flush</item>
  </style>
    <style name="Base.V21.Theme.MaterialComponents" parent="Base.V14.Theme.MaterialComponents">
    <item name="android:alertDialogTheme">@style/ThemeOverlay.MaterialComponents.Dialog.Alert.Frame