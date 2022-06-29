package com.aqulasoft.fireman.mobile.ui.postlogin;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aqulasoft.fireman.mobile.R;
import com.aqulasoft.fireman.mobile.base.utils.FiremanSettings;
import com.aqulasoft.fireman.mobile.base.utils.enums.ToastType;
import com.aqulasoft.fireman.mobile.databinding.ActivityMainBinding;
import com.aqulasoft.fireman.mobile.databinding.ToolbarLogoBinding;
import com.aqulasoft.fireman.mobile.ui.base.BaseActivity;
import com.aqulasoft.fireman.mobile.ui.base.BaseFragment;
import com.aqulasoft.fireman.mobile.ui.base.IconsProvider;
import com.aqulasoft.fireman.mobile.ui.base.OnBackPressListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.ref.WeakReference;
import java.util.prefs.Preferences;

import moxy.presenter.InjectPresenter;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mPresenter;

    private long mBackPressed;
    private NavController mNavController;

    private ActivityMainBinding mBinding;
    private ToolbarLogoBinding mToolbarBinding;

    private WeakReference<Fragment> mLateInitHomeAppBar;

    public static void start(Context context, @Nullable Object data) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Override
    public void openScreen(int nextPage, Parcelable data) {
        if (mNavController.getCurrentDestination() == null || nextPage != mNavController.getCurrentDestination().getId()) {
            
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //                         Activity
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupFragmentListenerForAppBarChange();

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Toolbar toolbar = mBinding.mainActivityToolBar;
        mToolbarBinding = ToolbarLogoBinding.inflate(getLayoutInflater(), toolbar, true);
        setSupportActionBar(toolbar);
        mToolbarBinding.toolBarBackButton.setOnClickListener(this::onBackClicked);

        mNavController = Navigation.findNavController(this, R.id.mainActivityNavHostFragment);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainActivityNavHostFragment);
                Fragment fragment = navHostFragment != null ? navHostFragment.getChildFragmentManager().getFragments().get(0) : null;
                if (fragment instanceof OnBackPressListener) {

                    boolean needClose = ((OnBackPressListener) fragment).onBackPress();
                    if (needClose)
                        return;
                }

                if (mNavController.getCurrentDestination() != null && mNavController.getCurrentDestination().getId() == mNavController.getGraph().getStartDestination()) {
                    if (mBackPressed + FiremanSettings.EXIT_TIME_INTERVAL > System.currentTimeMillis()) {
                        finish();
                        return;
                    } else {
                        showToast(R.string.exit_invite, ToastType.DEFAULT);
                    }

                    mBackPressed = System.currentTimeMillis();
                } else
                    mNavController.popBackStack();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLateInitHomeAppBar != null) {
            onDestinationChanged(null, mNavController.getCurrentDestination(), mLateInitHomeAppBar.get());
            mLateInitHomeAppBar.clear();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          butter knife
    ///////////////////////////////////////////////////////////////////////////

    public void onBackClicked(View view) {
        onBackPressed();
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          Main view
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void openWebPage(Uri uri) {
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          public
    ///////////////////////////////////////////////////////////////////////////

    public void onDestinationChanged(String name, NavDestination destination, Fragment fragment) {
        ActionBar actBar = getSupportActionBar();
        if (actBar == null) {
            return;
        }

        if (fragment instanceof IconsProvider) {
            setIcons(fragment);
        }

        if (fragment instanceof BaseFragment) {
            boolean isHome = destination.getId() == mNavController.getGraph().getStartDestinationId();
            setupToolbar(name, isHome, isHome);
        }
    }

    public void setIcons(@Nullable Fragment fragment) {
        Pair<View.OnClickListener, Integer> pair = ((BaseFragment<?>) fragment).getAdditionalIcon();
        if (pair != null) {
            mToolbarBinding.toolBarAdditionalButton.setImageResource(pair.second);
            mToolbarBinding.toolBarAdditionalButton.setOnClickListener(pair.first);
        }
        mToolbarBinding.toolBarAdditionalButton.setVisibility(pair == null ? View.INVISIBLE : View.VISIBLE);
    }

    public void setupToolbar(String name, boolean isRoot, boolean isHome) {
        mToolbarBinding.toolBarBackButton.setVisibility(!isRoot ? View.VISIBLE : View.GONE);

        if (!isHome && name != null) {
            mToolbarBinding.toolBarLogo.setVisibility(View.GONE);
            mToolbarBinding.toolBarTitle.setText(name);
            mToolbarBinding.toolBarTitle.setVisibility(View.VISIBLE);
        } else {
            mToolbarBinding.toolBarTitle.setVisibility(View.GONE);
            mToolbarBinding.toolBarLogo.setVisibility(View.VISIBLE);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          private
    ///////////////////////////////////////////////////////////////////////////

    private void setupFragmentListenerForAppBarChange() {
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);

                if (mNavController == null && f instanceof BaseFragment) {
                    mLateInitHomeAppBar = new WeakReference<>(f);

                } else if (mNavController != null) {
                    String name = v.getTag() == null ? null : v.getTag().toString();

                    onDestinationChanged(name, mNavController.getCurrentDestination(), f);
                }
            }
        }, true);

    }
}
