package com.mojang.ld22.gwt;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.mojang.ld22.Game;

import static com.google.gwt.dom.client.Style.Position.RELATIVE;
import static com.google.gwt.dom.client.Style.Unit.PX;


public class GwtMinicraft {
    private boolean isMobileBrowser;
    private SimplePanel _gameHolder;

    public void go() {
        String userAgent = Window.Navigator.getUserAgent();
        isMobileBrowser = userAgent.contains("Mobile");

        Game game = new Game(isMobileBrowser);
        _gameHolder = new SimplePanel(game);
        _gameHolder.getElement().getStyle().setProperty("margin", "30px auto"); //horizontally center

        _gameHolder.setWidth((Game.WIDTH * Game.SCALE) + "px");

        FlowPanel fp = new FlowPanel();
        fp.add(getControls(game));
        fp.add(_gameHolder);

        if (isMobileBrowser) {
            fp.add(getGamePad(game));
        } else {
            fp.add(new HTML("<br><br><br><br>" +
                            "<center>" +
                            "Minicraft HTML5<br>" +
                            "Minicraft was originally written by Markus Persson (Notch)<br>" +
                            "Converted to HTML5 via GWT/Canvas by Chi Hoang (chi at chi.ca)<br>" +
                            "<a href='http://github.com/chih/Minicraft-HTML5'>https://github.com/chih/Minicraft-HTML5</a><br>" +
                            "</center>"));
        }

        RootPanel.get("loadingMsg").getElement().setInnerHTML("");
        RootPanel.get().add(fp);

        if (isMobileBrowser) {
            Window.scrollTo(0, 1);
        }
        game.run();

    }

    private FlowPanel getGamePad(Game game) {
        FlowPanel fp = new FlowPanel();
        fp.getElement().getStyle().setPosition(RELATIVE);
        fp.getElement().getStyle().setHeight(700, PX);

        int oft = 48;

        GamePadButton up = new GamePadButton(game, KeyCodes.KEY_UP, "\u25B2",
                                             oft + 160, 00);
        GamePadButton down = new GamePadButton(game, KeyCodes.KEY_DOWN, "\u25BC",
                                               oft + 160, 200);
        GamePadButton left = new GamePadButton(game, KeyCodes.KEY_LEFT, "\u25C0",
                                               oft, 100);
        GamePadButton right = new GamePadButton(game, KeyCodes.KEY_RIGHT, "\u25B6",
                                                oft + 320, 100);
        GamePadButton x = new GamePadButton(game, 'X', "X",
                                            oft + 540, 100);
        GamePadButton c = new GamePadButton(game, 'C', "C",
                                            oft + 720, 100);

        fp.add(up);
        fp.add(down);
        fp.add(left);
        fp.add(right);
        fp.add(x);
        fp.add(c);

        return fp;
    }

    private class GamePadButton extends Label {
        private GamePadButton(final Game game,
                              final int nativeKeyCode,
                              final String lbl,
                              int x,
                              int y) {
            super(lbl);
            setStyleName("gamePadButton");
            getElement().getStyle().setLeft(x, PX);
            getElement().getStyle().setTop(y, PX);

            addTouchStartHandler(new TouchStartHandler() {
                @Override
                public void onTouchStart(TouchStartEvent event) {
                    game.input.toggle(nativeKeyCode, true);
                    getElement().getStyle().setBackgroundColor("blue");
                    event.preventDefault();
                }
            });

            addTouchEndHandler(new TouchEndHandler() {
                @Override
                public void onTouchEnd(TouchEndEvent event) {
                    game.input.toggle(nativeKeyCode, false);
                    getElement().getStyle().setBackgroundColor("");
                    event.preventDefault();
                }
            });
        }
    }

    private Widget getControls(final Game game) {
        HorizontalPanel inner = new HorizontalPanel();
        inner.setWidth("100%");
        inner.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        //isgodMode button
        final CheckBox isGodModeButton = new CheckBox("God Mode");
        isGodModeButton.getElement().getStyle().setMarginLeft(10, PX);
        isGodModeButton.getElement().getStyle().setMarginTop(4, PX);
        inner.add(isGodModeButton);
        isGodModeButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                game.setGodMode(booleanValueChangeEvent.getValue());
            }
        });

        //title
        Label title = new Label("Minicraft HTML5");
        inner.add(title);
        inner.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);


        //status
        Label statusWidget = new Label();
        statusWidget.getElement().getStyle().setMarginRight(10, PX);
        statusWidget.getElement().getStyle().setMarginTop(4, PX);
        inner.add(statusWidget);
        inner.setCellHorizontalAlignment(statusWidget, HasHorizontalAlignment.ALIGN_RIGHT);
        game.setStatusWidget(statusWidget);

        FlowPanel controlsOuter = new FlowPanel();
        controlsOuter.setStyleName("controlsOuter");
        controlsOuter.add(inner);
        if (isMobileBrowser) {
            controlsOuter.addStyleName("controlsBigger");
        }

        return controlsOuter;
    }
}
