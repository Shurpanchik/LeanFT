<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<applicationModel>
    <name>Application Model</name>
    <className>Resmon</className>
    <objects>
        <object type="UiObject" technology="Standard Windows">
            <name>BrowserTab</name>
            <codeName>BrowserTab</codeName>
            <identification>
                <properties>
                    <property name="Text" type="string" isRegExp="false">Обзор</property>
                </properties>
            </identification>
        </object>
        <object type="TabControl" technology="WinForms">
            <name>TabPanel</name>
            <codeName>TabPanel</codeName>
            <identification>
                <properties>
                    <property name="Tabs" type="collection" isRegExp="false" collectionType="string">
                        <item>Обзор</item>
                        <item>ЦП</item>
                        <item>Память</item>
                        <item>Диск</item>
                        <item>Сеть</item>
                    </property>
                    <property name="WindowClassRegExp" type="string" isRegExp="false">SysTabControl32</property>
                </properties>
            </identification>
            <children>
                <object type="UiObject" technology="WinForms">
                    <name>BrowserTab</name>
                    <codeName>BrowserTab</codeName>
                    <identification>
                        <properties>
                            <property name="Text" type="string" isRegExp="false">Обзор</property>
                        </properties>
                    </identification>
                </object>
            </children>
        </object>
    </objects>
</applicationModel>
