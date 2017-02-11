package scenarios;

import domain.JourneyDetails;
import domain.JourneyDetailsBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageFactoryImpl.FlightsSearchPage;
import pageFactoryImpl.LandingPage;
import pageFactoryImpl.SearchResultsPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Chapter_07_IntroducingDomainDrivenLanguage {

    // Use the application driver
    WebDriver driver;
    LandingPage onLandingPage;
    FlightsSearchPage onFlightsSearchPage;
    SearchResultsPage onResultsPage;



    @BeforeMethod
      public void setup(){
        //launch the application under test
        driver = new AppDriver().getDriver();
        driver.get("http://www.cleartrip.com/");
        onLandingPage = PageFactory.initElements(driver, LandingPage.class);
        onFlightsSearchPage = onLandingPage.goToFlightsSearchPage();

    }

    @Test
    public void testThatResultsAppearForAOneWayJourney(){

        //create your test data - the journey detail domain object
        JourneyDetails journeyDetails = new JourneyDetailsBuilder().isOneWay(true).
                withOrigin("Bangalore").withDestination("Delhi").
                withDepartureDate(tomorrow()).build();

        onResultsPage = onFlightsSearchPage.searchForAOneWayJourneyWith(journeyDetails);

        //verify that result appears for the provided journey search
        Assert.assertTrue(onResultsPage.resultsAppearForOutboundJourney());

    }


    @Test
    public void testThatResultsAppearForAReturnJourney(){

        //create your test data - the journey detail domain object
        JourneyDetails journeyDetails = new JourneyDetailsBuilder().isOneWay(false).
                withOrigin("Bangalore").withDestination("Delhi").
                withDepartureDate(tomorrow()).withReturnDate(dayAfterTomorrow()).build();

        onResultsPage = onFlightsSearchPage.searchForAReturnJourneyWith(journeyDetails);

        //verify that result appears for the provided journey search
        Assert.assertTrue(onResultsPage.resultsAppearForOutboundJourney());
        Assert.assertTrue(onResultsPage.resultsAppearForInboundJourney());

    }


    @AfterMethod
    public void teardown(){
        //close the browser
        driver.close();

    }


    public String tomorrow(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
    }

    public String dayAfterTomorrow(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 2);
        return new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
    }



}
