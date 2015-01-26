package com.tujuhlangit.skilltestreal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

/**
 * Created by Timothy on 1/26/2015.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private UiLifecycleHelper uiHelper;
    private boolean loggedIn = false;

    public LoginFragment() {

    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            this.loggedIn = true;
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            this.loggedIn = false;
        }
    }

    public boolean getStatusLogin() {
        return this.loggedIn;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        LoginButton fbLoginBtn = (LoginButton) rootView.findViewById(R.id.fb_login_button);
        fbLoginBtn.setFragment(new NativeFragmentWrapper(this));

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(),callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    public boolean isLoggedIn() {
        Session session = Session.getActiveSession();
        if(session != null)
            Log.i("LoginFragment7782", session.toString());
        return (session != null && session.isOpened());
    }

    public String sessionToString() {
        Session session = Session.getActiveSession();
        return (session != null ? session.toString() : "NULL");
    }

    public boolean isLoggedOut() {
        Session session = Session.getActiveSession();
        if(session != null)
            Log.i("LogoutFragment7782", session.toString());
        return (session != null && session.isClosed());
    }

    public class NativeFragmentWrapper extends android.support.v4.app.Fragment {
        private final Fragment nativeFragment;

        public NativeFragmentWrapper(Fragment nativeFragment) {
            this.nativeFragment = nativeFragment;
        }

        @Override
        public void startActivityForResult(Intent intent, int requestCode) {
            nativeFragment.startActivityForResult(intent, requestCode);
        }

        @Override
        public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
            nativeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
