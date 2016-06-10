package com.gyak.gworm;

import com.google.gson.JsonElement;
import com.gyak.proterty.NotInitRequestProperties;
import org.jsoup.select.Elements;


public interface GwormJsonable {

	String getId();

	JsonElement getJsonFromElements(Elements elements) throws NotInitRequestProperties;
	
}
