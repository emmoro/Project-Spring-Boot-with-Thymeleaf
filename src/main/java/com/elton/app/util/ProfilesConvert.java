package com.elton.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.elton.app.domain.Profile;

@Component
public class ProfilesConvert implements Converter<String[], List<Profile>> {
	
	@Override
	public List<Profile> convert(String[] source) {
		List<Profile> profiles = new ArrayList<>();
		for (String id : source) {
			if (!id.equals("0")) {
				profiles.add(new Profile(Long.parseLong(id)));
			}
		}
		return profiles;
	}

}
