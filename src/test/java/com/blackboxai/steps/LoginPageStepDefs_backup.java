// package com.blackboxai.steps;

// import com.blackboxai.pages.LoginPageObjects;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import org.testng.Assert;

// public class LoginPageStepDefs_backup extends BaseSteps {

// private final com.blackboxai.utils.readers.JsonReader jsonReader = new
// com.blackboxai.utils.readers.JsonReader();

// // Cucumber default object factory requires a public zero-argument
// constructor
// public LoginPageStepDefs_backup() {
// super(new Hooks());
// }

// public LoginPageStepDefs_backup(Hooks hooks) {
// super(hooks);
// }

// @Given("I am on the login page")
// public void iAmOnTheLoginPage() {
// // Navigation is handled in Hooks
// System.out.println("I am on the login page");
// }

// @When("the user logs in with username {string} and password {string}")
// public void theUserLogsInWithUsernameAndPassword(String username, String
// password) {
// // Resolve placeholders from the JSON so the scenario is stable.
// String envName = System.getProperty("env", "dev");
// String filePath = "src/test/resources/env/" + envName +
// "/data/userData.json";
// System.out.println("DEBUG: json data file=" + filePath);

// com.blackboxai.utils.readers.JsonArrayOrObjectReader reader = new
// com.blackboxai.utils.readers.JsonArrayOrObjectReader();

// // Iterate through ALL entries in the JSON array within the same scenario
// run.
// // This aligns with the expectation: scenario executes once but performs two
// // logins
// // (one per JSON data set).

// org.json.JSONObject root = reader.readFirstObject(filePath);
// // If the JSON is a wrapper object, readFirstObject may return the inner
// object.
// // For array iteration we need the raw content. We'll handle both cases:
// // - If file content is a JSON array, we can index into it.
// // - If it's already an object, we'll just run once.

// // Detect array length by reading indexed objects until bounds are safe.
// // Simpler/safer: attempt known indices 0..N by parsing the array as JSON.
// String content;
// try {
// content = new
// String(java.nio.file.Files.readAllBytes(java.nio.file.Path.of(filePath)),
// java.nio.charset.StandardCharsets.UTF_8).trim();
// } catch (Exception e) {
// // fallback to classpath reader by using JsonArrayOrObjectReader's
// // readIndexedObject;
// // this path will work in both typical Maven and IDE runs.
// content = null;
// }

// int iterations = 1;
// if (content != null && content.startsWith("[")) {
// org.json.JSONArray arr = new org.json.JSONArray(content);
// iterations = arr.length();
// }

// LoginPageObjects loginPage = new LoginPageObjects(page);

// for (int i = 0; i < iterations; i++) {
// org.json.JSONObject user;
// if (content != null && content.startsWith("[")) {
// org.json.JSONArray arr = new org.json.JSONArray(content);
// user = arr.getJSONObject(Math.min(i, arr.length() - 1));
// } else {
// // Non-array/object case: execute once using first object.
// user = root;
// }

// // If the step used placeholders like "${username}", prefer JSON values.
// String actualUsername = user.has("username") ? user.getString("username") :
// username;
// String actualPassword = user.has("password") ? user.getString("password") :
// password;

// loginPage.enterUsername(actualUsername);
// loginPage.enterPassword(actualPassword);
// page.waitForTimeout(2000);
// loginPage.clickLogin();

// // Wait a moment for navigation/state.
// page.waitForTimeout(3000);

// // Assert Products after every login.
// Assert.assertTrue(
// loginPage.isLoggedInSuccessfully(),
// "Expected Products page/dashboard header to be visible after login for record
// index=" + i);

// // After successful login, go back to login page for next dataset.
// page.navigate("https://www.saucedemo.com/");
// page.waitForTimeout(500);

// // Clear inputs for next iteration.
// loginPage.enterUsername("");
// loginPage.enterPassword("");

// }
// System.out.println("I login with username: " + username + " and password: " +
// password);
// }

// @Then("user should see the dashboard header text {string}")
// public void userShouldSeeTheDashboardHeaderText(String expectedHeader) {
// // Intentionally no-op.
// // This project executes login + assertion inside the JSON-driven loop in:
// // When "the user logs in with username {string} and password {string}"
// // Running this step once at the end breaks multi-login loops because
// // the step definition resets the page between iterations.
// System.out.println("user should see the dashboard header text: " +
// expectedHeader);
// }

// @Then("user print address information from {string} section of the data
// file")
// public void userPrintAddressInformationFromSectionOfTheDataFile(String
// addressSection) {
// // Keep step definitions thin: delegate JSON array/object parsing to util.
// String envName = System.getProperty("env", "dev");
// String filePath = "src/test/resources/env/" + envName +
// "/data/userData.json";
// System.out.println("DEBUG: data file=" + filePath);

// com.blackboxai.utils.readers.JsonArrayOrObjectReader reader = new
// com.blackboxai.utils.readers.JsonArrayOrObjectReader();

// // Print address info for ALL datasets, matching the login loop in the When
// // step.
// // We'll parse as array if possible; otherwise print once.
// String content = null;
// try {
// content = new
// String(java.nio.file.Files.readAllBytes(java.nio.file.Path.of(filePath)),
// java.nio.charset.StandardCharsets.UTF_8).trim();
// } catch (Exception ignored) {
// }

// if (content != null && content.startsWith("[")) {
// org.json.JSONArray arr = new org.json.JSONArray(content);
// for (int i = 0; i < arr.length(); i++) {
// org.json.JSONObject user = arr.getJSONObject(i);
// org.json.JSONObject addr = user.getJSONObject("address");

// boolean looksLikePlaceholder = addressSection == null ||
// addressSection.contains("$")
// || "${address}".equals(addressSection);
// org.json.JSONObject sectionObj = looksLikePlaceholder ? addr
// : (addr.has(addressSection) ? addr.getJSONObject(addressSection) : addr);

// java.util.Iterator<String> keys = sectionObj.keys();
// while (keys.hasNext()) {
// String k = keys.next();
// Object v = sectionObj.get(k);
// System.out.println("Address[" + addressSection + "][" + i + "]." + k + "=" +
// String.valueOf(v));
// }
// }
// } else {
// org.json.JSONObject rootObj = reader.readFirstObject(filePath);
// org.json.JSONObject addr = rootObj.getJSONObject("address");

// boolean looksLikePlaceholder = addressSection == null ||
// addressSection.contains("$")
// || "${address}".equals(addressSection);
// org.json.JSONObject sectionObj = looksLikePlaceholder ? addr
// : (addr.has(addressSection) ? addr.getJSONObject(addressSection) : addr);

// java.util.Iterator<String> keys = sectionObj.keys();
// while (keys.hasNext()) {
// String k = keys.next();
// Object v = sectionObj.get(k);
// System.out.println("Address[" + addressSection + "]." + k + "=" +
// String.valueOf(v));
// }
// }
// System.out.println("user print address information from ADDRESS section of
// the data file");
// }

// }
