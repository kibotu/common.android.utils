package com.common.android.utils.input;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.common.android.utils.interfaces.LogTag;

/**
 * Created by paul.sprotte on 19.05.16.
 * <p>
 * <a href="https://developer.amazon.com/appsandservices/solutions/devices/fire-tv/docs/amazon-fire-tv-remote-input">https://developer.amazon.com/appsandservices/solutions/devices/fire-tv/docs/amazon-fire-tv-remote-input</a>
 * <p>
 * <pre>
 *  ----------------------------------------------------------------------------------------------------
 * |                            |       (KEYCODE_MEDIA_RECORD)      |                                   |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |                            |       KEYCODE_DPAD_UP             |                                   |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_DPAD_LEFT       |       KEYCODE_DPAD_CENTER         |       KEYCODE_DPAD_RIGHT          |
 *  ----------------------------------------------------------------------------------------------------
 * |                            |       KEYCODE_DPAD_DOWN           |                                   |
 *  ----------------------------------------------------------------------------------------------------
 * |                                                                                                    |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_BACK            |        (KEYCODE_ESCAPE)           |       KEYCODE_MENU                |
 *  ----------------------------------------------------------------------------------------------------
 * |    KEYCODE_MEDIA_REWIND    |        KEYCODE_MEDIA_PLAY_PAUSE   |       KEYCODE_MEDIA_FAST_FORWARD  |
 *  ----------------------------------------------------------------------------------------------------
 *
 *  </pre>
 * <img src="https://developer.amazon.com/public/binaries/content/gallery/remote-callouts.png" />
 * <pre>
 *  Button	                                    KeyEvent	                Default Behavior
 *  Home	                                    none	                    Return the user to the Home screen. This is a system event and cannot be intercepted.
 *  Back	                                    KEYCODE_BACK	            Return the user to the previous operation or screen (Activity).
 *  Menu	                                    KEYCODE_MENU	            Invoke the Android context menu (OptionsMenu).
 *  Microphone (Search) (Voice Remote only)     none	                    Invoke the system voice search. This is a system event and cannot be intercepted.
 *  Select (D-Pad Center)	                    KEYCODE_DPAD_CENTER	        Select the user interface item with the current focus.
 *  Up (D-Pad)	                                KEYCODE_DPAD_UP	            Move the focus upward in the user interface.
 *  Down (D-Pad)	                            KEYCODE_DPAD_DOWN	        Move the focus downward in the user interface.
 *  Left (D-Pad)	                            KEYCODE_DPAD_LEFT	        Move the focus left in the user interface.
 *  Right (D-Pad)	                            KEYCODE_DPAD_RIGHT	        Move the focus right in the user interface.
 *  Play/Pause	                                KEYCODE_MEDIA_PLAY_PAUSE	Control media playback. Play/Pause is a toggle.
 *  Rewind	                                    KEYCODE_MEDIA_REWIND	    Rewind or skip backwards in media playback contexts.
 *  Fast Forward	                            KEYCODE_MEDIA_FAST_FORWARD	Fast Forward or skip ahead in media playback contexts.
 * </pre>
 */
public class AmazonFireTvRemoteControlListener implements KeyListener, View.OnKeyListener, DialogInterface.OnKeyListener, LogTag {

    private View currentView;

    @Nullable
    public final View getCurrentView() {
        return this.currentView;
    }

    @Override
    final public boolean onKey(final View v, final int keyCode, @NonNull final KeyEvent event) {
        this.currentView = v;
        return event.getAction() == KeyEvent.ACTION_DOWN
                ? onKeyDown(keyCode, event)
                : event.getAction() == KeyEvent.ACTION_UP && onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKey(final DialogInterface dialog, final int keyCode, @NonNull final KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN
                ? onKeyDown(keyCode, event)
                : event.getAction() == KeyEvent.ACTION_UP && onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                return onKeyUpDpadUp();
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onKeyUpDpadDown();
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onKeyUpDpadLeft();
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onKeyUpDpadRight();
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return onKeyUpDpadCenter();
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                return onKeyUpMediaPlayPause();
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return onKeyUpMediaRewind();
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return onKeyUpMediaFastForward();
            case KeyEvent.KEYCODE_BACK:
                return onKeyUpBack();
            case KeyEvent.KEYCODE_MENU:
                return onKeyUpMenu();
            default:
                return false;
        }
    }

    protected boolean onKeyUpDpadUp() {
        return false;
    }

    protected boolean onKeyUpDpadDown() {
        return false;
    }

    protected boolean onKeyUpDpadLeft() {
        return false;
    }

    protected boolean onKeyUpDpadRight() {
        return false;
    }

    protected boolean onKeyUpDpadCenter() {
        return false;
    }

    protected boolean onKeyUpMediaPlayPause() {
        return false;
    }

    protected boolean onKeyUpMediaRewind() {
        return false;
    }

    protected boolean onKeyUpMediaFastForward() {
        return false;
    }

    protected boolean onKeyUpBack() {
        return false;
    }

    protected boolean onKeyUpMenu() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                return onKeyDownDpadUp();
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onKeyDownDpadDown();
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onKeyDownDpadLeft();
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onKeyDownDpadRight();
            case KeyEvent.KEYCODE_DPAD_CENTER:
                return onKeyDownDpadCenter();
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                return onKeyDownMediaPlayPause();
            case KeyEvent.KEYCODE_MEDIA_REWIND:
                return onKeyDownMediaRewind();
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
                return onKeyDownMediaFastForward();
            case KeyEvent.KEYCODE_BACK:
                return onKeyDownBack();
            case KeyEvent.KEYCODE_MENU:
                return onKeyDownMenu();
            default:
                return false;
        }
    }

    protected boolean onKeyDownDpadUp() {
        return false;
    }

    protected boolean onKeyDownDpadDown() {
        return false;
    }

    protected boolean onKeyDownDpadLeft() {
        return false;
    }

    protected boolean onKeyDownDpadRight() {
        return false;
    }

    protected boolean onKeyDownDpadCenter() {
        return false;
    }

    protected boolean onKeyDownMediaPlayPause() {
        return false;
    }

    protected boolean onKeyDownMediaRewind() {
        return false;
    }

    protected boolean onKeyDownMediaFastForward() {
        return false;
    }

    protected boolean onKeyDownBack() {
        return false;
    }

    protected boolean onKeyDownMenu() {
        return false;
    }

    @NonNull
    @Override
    public String tag() {
        return AmazonFireTvRemoteControlListener.class.getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmazonFireTvRemoteControlListener that = (AmazonFireTvRemoteControlListener) o;

        return currentView != null ? currentView.equals(that.currentView) : that.currentView == null;

    }

    @Override
    public int hashCode() {
        return currentView != null ? currentView.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AmazonFireTvRemoteControlListener{" +
                "currentView=" + currentView +
                '}';
    }
}
