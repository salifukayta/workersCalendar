package ro.stancalau.springfx.gui;

import sol.workers.calendar.config.ScreensConfig;
import sol.workers.calendar.presenter.AbstractPresenter;

public abstract class Modal extends AbstractPresenter {

    protected ModalDialog dialog;

    public Modal(ScreensConfig config) {
        super(config);
    }

    public void setDialog(ModalDialog dialog) {
        this.dialog = dialog;
    }
}