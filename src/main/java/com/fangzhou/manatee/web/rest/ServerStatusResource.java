// package com.fangzhou.manatee.web.rest;

// import javax.inject.Inject;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.security.access.annotation.Secured;
// import com.codahale.metrics.annotation.Timed;

// @RestController
// @RequestMapping("/api")
// public class ServerStatusResource {

// 	static String cached_variable = null;

// 	/**
//      * fetches the change list for an entity class in the current day
//      *
//      * @return
//      */
//     @RequestMapping(value = "/layout/current",
//         method = RequestMethod.GET,
//         produces = MediaType.APPLICATION_JSON_VALUE)
//     @Timed
//     @Secured(AuthoritiesConstants.USER)
//     public String getCurrentLayout() {
//     	return cached_variable;
//     }
// }