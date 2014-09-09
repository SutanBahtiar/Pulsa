package org.stn.pulsa;

import org.stn.pulsa.controller.SettingsController;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// NativeCSS.setDebugLogging(true);
		// String css = NativeCSSUtils.readFileFromAssets(this, "styles.css");
		// NativeCSS.styleWithCSS(css);

		// try {
		// URL css = new URL("http://berak.url.ph/styles.css");
		// NativeCSS.styleWithCSS("styles.css", css, Never);
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.drawer_list);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				invalidateOptionsMenu();
			}
		};

		drawerLayout.setDrawerListener(actionBarDrawerToggle);

		adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, getResources().getStringArray(R.array.menus));
		drawerList.setAdapter(adapter);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();

				// Creating a fragment transaction
				FragmentTransaction ft = fragmentManager.beginTransaction();

				if (position == 0) {
					HomeActivity home = new HomeActivity();

					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, home);

					// Committing the transaction
					ft.commit();
				} else if (position == 1) {
					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, new PembelianActivity());

					// Committing the transaction
					ft.commit();
				} else if (position == 2) {
					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, new PenjualanActivity());

					// Committing the transaction
					ft.commit();

				} else if (position == 3) {
					MasterActivity masterActivity = new MasterActivity();

					// Adding a fragment to the fragment transaction
					ft.replace(R.id.content_frame, masterActivity);

					// Committing the transaction
					ft.commit();
				} else if (position == 4) {
					// Adding a fragment to the fragment transaction
					SettingsController con = new SettingsController(MainActivity.this);
					con.open();
					int count = con.getCount();
					con.close();

					if (count > 0) {
						ft.replace(R.id.content_frame, new SettingsDaftarActivity());
					} else {
						ft.replace(R.id.content_frame, new SettingsInputActivity());
					}

					// Committing the transaction
					ft.commit();
				}

				getActionBar().setTitle(adapter.getItem(position).replace("Home", "Pulsa"));
				drawerLayout.closeDrawer(drawerList);

			}
		});

		FragmentManager fragmentManager = getFragmentManager();

		// Creating a fragment transaction
		FragmentTransaction ft = fragmentManager.beginTransaction();

		// Adding a fragment to the fragment transaction
		ft.replace(R.id.content_frame, new HomeActivity());

		// Committing the transaction
		ft.commit();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		// menu.findItem(R.id.menuPembelian).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!drawerLayout.isDrawerOpen(drawerList)) {
				drawerLayout.openDrawer(drawerList);
			} else {
				drawerLayout.closeDrawer(drawerList);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
