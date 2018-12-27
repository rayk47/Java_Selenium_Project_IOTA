package framework.ui.entities;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
@JsonInclude(Include.NON_NULL)
@Getter @Setter
public class NavigationEntity implements Serializable {

	String navigation_name;
	String navigation_url;
	NavigationOptions[] navigation_options;
	static  ObjectMapper mapper = new ObjectMapper();

	public static NavigationEntity[] convertJsonFileToNavigation(File file) throws JsonParseException, JsonMappingException, IOException {
		NavigationEntity[] navigationEntity = mapper.readValue(file, NavigationEntity[].class);
		return navigationEntity;
	}
	
	public static List<String> getListOfNavigationNames(NavigationEntity[] nav){
		List<NavigationEntity> list = Arrays.asList(nav);
		return list.stream().map(x -> x.getNavigation_name()).collect(Collectors.toList());
	}
	
	public static List<String> getListOfNavigationOptions(NavigationEntity nav){
		List<NavigationOptions> list = Arrays.asList(nav.getNavigation_options());
		return list.stream().map(x -> x.getNavigation_option_name()).collect(Collectors.toList());
	}
}
