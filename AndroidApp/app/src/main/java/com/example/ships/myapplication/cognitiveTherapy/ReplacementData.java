package com.example.ships.myapplication.cognitiveTherapy;

/**
 * Created by user on 2017/9/15.
 */

class ReplacementData {
    public static void initializeReplacement(ReplacementHandler db) {

        db.addContent(new ReplacementContent(1, "SHOULDS\nChange from demands to preferences","Preferences:\nI \'d like to\nI \'d prefer\nI \'d rather\nIt would be better if\nI want to\nI choose to", "Demands:\nShould\nMust\nHave to\nOught to\nGot to\nSupposed to\nNeed"));
        db.addContent(new ReplacementContent(2, "AWFULISING\nChange from catastrophic to realistic","Realistic:\nIt\'s uncomfortable\nIt\'s unpleasant\nIt\'s a nuisance\nIt\'s undesirable\nI don\'t like it\nIt\'s embarrassing\n", "Catastrophic:\nTerrible\nAwful\nHorrible\nDigusting\nCan\'t stand it\nUnbearable"));
        db.addContent(new ReplacementContent(3, "ABSOLUTE\nChange from absolutes to relative","Relative:\nSometimes","Absolutes:\nAlways\nNever"));
        db.addContent(new ReplacementContent(4, "SELF-LIMITING\nChange from self-limiting to self-enhancing","Self-enhancing:\nCan\nWon\'t\nHaven\'t up till now","Self-limiting:\nCan\'t"));
        db.addContent(new ReplacementContent(5, "BEING (TOTAL SELF)\nChange from being (total self) to doing/behaviour","Doing/Behaviour:\nI do","Being (Total self):\nI am"));
        db.addContent(new ReplacementContent(6, "MISCELLANEOUS","And\nI\nThis is the way I see it","But\nYou\nThis is how it is"));
    }
}
