package cz.mg.toolkit.impl;


public interface ImplKeyboard {
    public boolean isCapsLockActive();
    public boolean isNumLockActive();
    public boolean isScrollLockActive();
    public void setCapsLockActive(boolean value);
    public void setNumLockActive(boolean value);
    public void setScrollLockActive(boolean value);
}
