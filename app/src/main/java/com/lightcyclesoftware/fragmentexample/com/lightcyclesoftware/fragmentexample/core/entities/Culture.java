package com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities;

/**
 * Created by ewilliams on 2/23/15.
 */
public class Culture {

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCultureCode() {
        return cultureCode;
    }

    public void setCultureCode(String cultureCode) {
        this.cultureCode = cultureCode;
    }

    public String getDisplayCultureName() {
        return displayCultureName;
    }

    public void setDisplayCultureName(String displayCultureName) {
        this.displayCultureName = displayCultureName;
    }

    public String getEnglishCultureName() {
        return englishCultureName;
    }

    public void setEnglishCultureName(String englishCultureName) {
        this.englishCultureName = englishCultureName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    private String countryCode;
    private String cultureCode;
    private String displayCultureName;
    private String englishCultureName;
    private String languageCode;

}
