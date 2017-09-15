package com.example.ships.myapplication.cognitiveTherapy;

/**
 * Created by user on 2017/9/15.
 */

class ReplacementData {
    public static void initializeReplacement(ReplacementHandler db) {

        db.addContent(new ReplacementContent(1, "SHOULDS","I \'d like to\nI \'d prefer\nI \'d rather\nIt would be better if\nI want to\nI choose to", "Should\nMust\nHave to\nOught to\nGot to\nSupposed to\nNeed"));
        db.addContent(new ReplacementContent(2, "AWFULISING","It\'s uncomfortable\nIt\'s unpleasant\nIt\'s a nuisance\nIt\'s undesirable\nI don\'t like it\nIt\'s embarrassing\n", "Terrible\nAwful\nHorrible\nDigusting\nCan\'t stand it\nUnbearabble"));
        db.addContent(new ReplacementContent(3, "ABSOLUTE","Sometimes","Always\nNever"));
        db.addContent(new ReplacementContent(4, "SELF-LIMITING","Can\nWon\'t\nHaven\'t up till now","Can\'t"));
        db.addContent(new ReplacementContent(5, "BEING (TOTAL SELF)","I do","I am"));
        db.addContent(new ReplacementContent(6, "MISCELLANEOUS","And\nI\nThis is the way I see it","But\nYou\nThis is how it is"));
    }
}
