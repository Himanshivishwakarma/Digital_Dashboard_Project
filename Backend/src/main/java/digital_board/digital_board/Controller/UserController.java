package digital_board.digital_board.Controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import digital_board.digital_board.Dto.AuthResponse;

@RestController
public class UserController {

    // Inject your RestTemplate bean or create it as needed

    // @GetMapping("/create")
    // public ResponseEntity<String> createUser() {
    // String auth0Domain = "dev-11fo0ea2adho6vqb.us.auth0.com";
    // String auth0ManagementToken = "6539f604fe9651b9b5ddf4ed";

    // // Prepare the request data
    // JSONObject request = new JSONObject();
    // request.put("email", "norman.lewis@email.com");
    // request.put("given_name", "Norman");
    // request.put("family_name", "Lewis");
    // request.put("connection", "Username-Password-Authentication");
    // request.put("password", "Pa33w0rd");

    // // Set up the request headers, including the Auth0 Management API token
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // headers.set("Authorization", "Bearer " + auth0ManagementToken);

    // HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

    // // Send the POST request to create the user
    // String auth0ManagementApiUrl = "https://" + auth0Domain + "/api/v2/users";
    // ResponseEntity<String> result =
    // restTemplate.postForEntity(auth0ManagementApiUrl, entity, String.class);

    // // Check the response and return it
    // if (result.getStatusCode().is2xxSuccessful())
    // {
    // return ResponseEntity.ok("User created successfully");
    // }
    // else
    // {
    // return ResponseEntity.status(result.getStatusCode()).body("Error creating
    // user: " + result.getBody());
    // }
    // }

    // @GetMapping("/")
    // public String home(@AuthenticationPrincipal OidcUser principal)
    // {
    // if (principal != null) {
    // // model.addAttribute("profile", principal.getClaims());
    // System.out.println(principal.getClaims());
    // }
    // return "index";
    // }

    // create user

    // @GetMapping(value = "/createUser")
    // @ResponseBody
    // public ResponseEntity<String> createUser(HttpServletResponse response) {
    //     JSONObject request = new JSONObject();
    //     request.put("email", "norman.lewis@email.com");
    //     request.put("given_name", "Norman");
    //     request.put("family_name", "Lewis");
    //     request.put("connection", "Username-Password-Authentication");
    //     request.put("password", "Pa33w0rd");

    //     // ...
    //     ResponseEntity<String> result = restTemplate
    //             .postForEntity("https://dev-11fo0ea2adho6vqb.us.auth0.com/api/v2/users", request.toString(),
    //                     String.class);
    //     return result;
    // }

    // access token
    // public String getManagementApiToken() {
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);

    //     JSONObject requestBody = new JSONObject();
    //     requestBody.put("client_id", "SQ6iT4W1K8p4QYiXd484d9bCRdGEGTdX");
    //     requestBody.put("client_secret", "baKE4CV4_hXBGDfw2DaEQWkCJ_JskRhEo5a3kLRtWz3ER5w5C2lv01zCGZ9Ig43J");
    //     requestBody.put("audience", "https://dev-11fo0ea2adho6vqb.us.auth0.com/api/v2/");
    //     requestBody.put("grant_type", "client_credentials");

    //     HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);

    //     RestTemplate restTemplate = new RestTemplate();
    //     HashMap<String, String> result = restTemplate
    //             .postForObject("https://dev-11fo0ea2adho6vqb.us.auth0.com/oauth/token", request, HashMap.class);
    //     return result.get("access_token");
    // }

    // get all user
    // @GetMapping(value = "/users")
    // @ResponseBody
    // public ResponseEntity<String> users(HttpServletRequest request,
    // HttpServletResponse response) {
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // // headers.set("Authorization", "Bearer " + getManagementApiToken());
    // HttpEntity<String> entity = new HttpEntity<String>(headers);

    // RestTemplate restTemplate = new RestTemplate();
    // ResponseEntity<String> result = restTemplate
    // .exchange("https://dev-11fo0ea2adho6vqb.us.auth0.com/api/v2/users",
    // HttpMethod.GET, entity,
    // String.class);

    // return result;
    // }

    @GetMapping("/test")
    public ResponseEntity<AuthResponse> home(@AuthenticationPrincipal OidcUser principal) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(principal.getIdToken().getTokenValue());
        // authResponse.setRefreshtoken(principal.getAccessTokenHash());
        // System.out.println("User info==>" + principal.getClass());
        return ResponseEntity.ok(authResponse);
    }


    // @GetMapping("/get")
    // GetRequest getsingleuser()
    // {
         

    //     return  Unirest.get("https://dev-11fo0ea2adho6vqb.us.auth0.com/api/v2/users/auth0|653a1620ec5fd96daa7cd4a5");
    // }


    
    private static final String FIT_URL = "https://dev-11fo0ea2adho6vqb.us.auth0.com/api/v2/users/auth0|653a1620ec5fd96daa7cd4a5";

    @GetMapping("/testing")
    public static Object getDataFromFitUrl() throws Exception {
        // Create a RestTemplate object
        RestTemplate restTemplate = new RestTemplate();

        // Make a GET request to the fit URL
        
        ResponseEntity<String> response = restTemplate.getForEntity(FIT_URL, String.class);

        // Parse the response as JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataObject = mapper.readTree(response.getBody());

        // Return the data object from the JSON response
        return dataObject.get("data");
    }
    
}
/**
 * InnerUserController
 */
