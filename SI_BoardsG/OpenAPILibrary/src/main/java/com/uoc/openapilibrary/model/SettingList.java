package com.uoc.openapilibrary.model;

import java.util.ArrayList;
import com.uoc.openapilibrary.Constants;
import com.google.gson.Gson;
import com.uoc.openapilibrary.RESTMethod;

public class SettingList {
	private ArrayList<Setting> settings;

	public SettingList() {
		// Default constructor
	}

	public ArrayList<Setting> getSettings() {
		return settings;
	}

	public void setSettings(ArrayList<Setting> settings) {
		this.settings = settings;
	}

	public static SettingList getSettingListWS(String token) {
		String stl = RESTMethod.Get(
				Constants.BASEURI +"user/settings",
				token);

		return JSONtoSettingList(stl);
	}

	private static SettingList JSONtoSettingList(String settingList) {
		return new Gson().fromJson(settingList, SettingList.class);
	}
}

