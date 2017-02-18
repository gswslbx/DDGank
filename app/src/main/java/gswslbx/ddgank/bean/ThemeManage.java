package gswslbx.ddgank.bean;


/**
 * Created by Gswslbx on 2017/2/16.
 * 主题色管理类
 */

public enum ThemeManage {
    INSTANCE;

    private int colorPrimary;

    public void initColorPrimary(int colorPrimary) {
        setColorPrimary(colorPrimary);
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

}
