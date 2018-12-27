package framework.ui.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter @Setter
public class NavigationOptions implements Serializable {

	String navigation_option_name;
	String navigation_option_url;
	
}
