package no.ntnu.idatg2001;


import no.ntnu.idatg2001.FrontEnd.View.ApplicationStart;


public class Main {

  public static void main(String[] args) {
    /*
        StoryFileReader storyReader = new StoryFileReader();
    for (Story story : storyReader.readFile("src/main/resources/story.Paths")) {
      System.out.println(story);
    }
     */
    ApplicationStart.startApplication(args);
  }
}