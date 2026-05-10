package com.blackboxai.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPageObjects extends BasePage {

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorContainer;

    public LoginPageObjects(Page page) {
        super(page);
        this.usernameInput = page.locator("#user-name");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("#login-button");
        this.errorContainer = page.locator("h3[data-test='error']");
    }

    public void enterUsername(String username) {
        usernameInput.fill(username);
    }

    public void enterPassword(String password) {
        passwordInput.fill(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isErrorVisible() {
        return errorContainer.isVisible();
    }

    public boolean isLoggedInSuccessfully() {
        // After successful login, sauce demo shows "Products" header.
        return page.locator("span.title").first().isVisible();
    }
}


