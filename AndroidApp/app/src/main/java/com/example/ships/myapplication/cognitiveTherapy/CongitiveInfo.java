package com.example.ships.myapplication.cognitiveTherapy;

/**
 * Created by user on 2017/9/15.
 */

class CongitiveInfo {
    public static void initializeCognitiveInfo(DistortionHandler db) {
        db.addContent(new FactSheetContent(1, "1.All-or-Nothing Thinking", "You see things in black-and-white categories. If your performance falls short of perfect, you see yourself as a total failure."));
        db.addContent(new FactSheetContent(2, "2. Overgeneralisation", "You see a single negative event as a never-ending pattern of defeat."));
        db.addContent(new FactSheetContent(3, "3. Mental Filter", "You pick out a single negative detail and dwell on it exclusively so that your vision of all reality becomes darkened, like the drop of ink that discolours the entire beaker of water."));
        db.addContent(new FactSheetContent(4, "4. Disqualifying the Positive", "You reject positive experiences by insisting they \"don\'t count\" for some reasons or other. In this way you can maintain a negative belief that is contradicted by your everyday experiences."));
        db.addContent(new FactSheetContent(5, "5. Jumping to Conclusions", "You make a negative interpretation even though there are no definite facts that convincingly support your conclusion."));
        db.addContent(new FactSheetContent(6, "6. Magnification (Catastrophising) or Minimisation", "You exaggerate the importance of things (such as your mistakes or someone else\'s achievements) or you inappropriately shrink things until they appear tiny (your own desirable qualities or someone else\'s imperfections). This is also called the \"binocular trick\"."));
        db.addContent(new FactSheetContent(7, "7. Emotion Reasoning", "You assume that your negative emotions necessarily reflect the way things really are: \"I feel bad, therefore I must be bad\"."));
        db.addContent(new FactSheetContent(8, "8. Should Statements", "You try to motivate yourself with shoulds and should nots, as if you had to be whipped and punished before you could be expected to do anything. \"Musts\" are also offenders. The emotional consequence is guilt. When you direct should statements to others, you feel anger, frustration and resentment."));
        db.addContent(new FactSheetContent(9, "9. Labeling and Mislabeling", "This is an extreme form of overgeneralization. Instead of describing your error, you attach a negative label to yourself: \"I am a loser\". When someone else\'s behaviour bothers you, you attach a negative label to it \"He is lazy\". Labeling sticks to the person and does not describe the behaviour."));
        db.addContent(new FactSheetContent(10, "10. Personalisation", "You see yourself as the cause of some negative external event, which in fact you were not primarily responsible for."));
    }
}
