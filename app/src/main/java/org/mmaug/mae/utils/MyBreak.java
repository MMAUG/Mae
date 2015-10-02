/*
 * MyBreak.java
 *
 * Created on August 9, 2006, 1:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.mmaug.mae.utils;


/**
 *
 * @author User
 */
class MyBreak {

    /** Creates a new instance of MyBreak */
    public MyBreak() {
    }

    public static String decode(String s){
        String xs = "";
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            c -= 0x1041;
            xs += c;
        }
        return xs;
    }

    public static String parser(String s) {
        char bk = '\u200b';
        char bd = '\u200d';
        StringBuffer sb = new StringBuffer();
        char[] carr = s.toCharArray();
        for (int i = 0; i < carr.length; i++) {
            char ch = carr[i];
            try {
                switch (classReturner(ch)) {

                    case 0:
                        sb.append(ch);
                        sb.append(bk);
                        break;
                    case 1:
                        if (sb.charAt(sb.length() - 1) == bk) {
                            //sb[sb.Length - 1] = ch;
                            sb.setCharAt(sb.length() - 1, ch);
                            sb.append(bk);
                        } else {
                            sb.append(ch);
                            sb.append(bk);
                        }
                        break;
                    case 2:
                        sb.append(ch);
                        break;
                    case 3:
                        if (classReturner(sb.charAt(sb.length() - 4)) == 5) //if (classReturner(sb[sb.Length - 4]) == "c-kinzi")
                        {
                            sb.setCharAt(sb.length() - 3, bd);
                            //sb[sb.Length - 3] = bd;
                            sb.setCharAt(sb.length() - 1, ch);
                            //sb[sb.Length - 1] = ch;
                            sb.append(bk);
                        } else if (classReturner(sb.charAt(sb.length() - 2)) == 0) //else if (classReturner(sb[sb.Length - 2]) == "c-con")
                        {
                            sb.setCharAt(sb.length() - 3, sb.charAt(sb.length() - 2));
                            //sb[sb.Length - 3] = sb[sb.Length - 2];
                            sb.setCharAt(sb.length() - 2, ch);
                            //sb[sb.Length - 2] = ch;
                            sb.setCharAt(sb.length() - 1, bk);
                            //sb[sb.Length - 1] = bk;
                        } else {
                            sb.setCharAt(sb.length() - 1, ch);
                            //sb[sb.Length - 1] = ch;
                            sb.append(bk);
                        }
                        break;
                    case 4:
                    case 5:
                        if (classReturner(sb.charAt(sb.length() - 3)) == 1) //if (classReturner(sb[sb.Length - 3]) == "c-fatt")
                        {
                            if (classReturner(sb.charAt(sb.length() - 4)) == 2) //if (classReturner(sb[sb.Length - 4]) == "c-fatt")
                            {
                                if (sb.charAt(sb.length() - 5) == bk) //if (sb[sb.Length - 5] == bk)
                                {
                                    sb.setCharAt(sb.length() - 5, bd);
                                    //sb[sb.Length - 5] = bd;
                                }
                                sb.setCharAt(sb.length() - 1, ch);
                                //sb[sb.Length - 1] = ch;
                                sb.append(bk);
                            } else if (sb.charAt(sb.length() - 4) == bk) //else if (sb[sb.Length - 4] == bk)
                            {
                                sb.setCharAt(sb.length() - 4, bd);
                                //sb[sb.Length - 4] = bd;
                                sb.setCharAt(sb.length() - 1, ch);
                                //sb[sb.Length - 1] = ch;
                                sb.append(bk);
                            }
                        } else {
                            sb.setCharAt(sb.length() - 3, sb.charAt(sb.length() - 2));
                            //sb[sb.Length - 3] = sb[sb.Length - 2];
                            sb.setCharAt(sb.length() - 2, ch);
                            //sb[sb.Length - 2] = ch;
                            sb.setCharAt(sb.length() - 1, bk);
                            //sb[sb.Length - 1] = bk;
                        }
                        break;
                    default:
                        sb.append(ch);
                        break;
                }
            }catch (Exception ex){

            }
        }
        return sb.toString();
    }

    public static String XPartial2Unicode(String s) {
        StringBuffer vowels = new StringBuffer();
        StringBuffer svowels = new StringBuffer();
        StringBuffer mesials = new StringBuffer();
        String staker = "";
        String consonant = "";
        String killer = "";
        String tones = "";
        String kinzi = "";
        StringBuffer final_ = new StringBuffer();
        boolean stacked = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int _case = classReturner2(c);
            if (i == s.length()) {
                _case = 10;
            }
            switch (_case) {
                case 0:
                    if ((i + 1) <= s.length() - 1) {
                        if (s.charAt(i + 1) == '\u1039' || s.charAt(i + 1) == '\u103a') {
                            killer += NormalizeCon(c);
                            continue;
                        }
                    }
                    consonant += NormalizeCon(c);
                    break;
                case 2:
                    if (stacked) {
                        svowels.append(NormalizeVowels(c));
                    } else {
                        vowels.append(NormalizeVowels(c));
                    }
                    break;
                case 1:
                    mesials.append(NormalizeMesials(c));
                    break;
                case 4:
                    staker += NormalizeStackers(c);
                    stacked = true;
                    break;
                case 3:
                    if ((i - 1) > -1) {
                        if (classReturner2(s.charAt(i - 1)) == 2) {
                            vowels.append('\u103a');
                        }
                    }
                    break;
                case 5:
                    if (c == 'ၦ') {
                        vowels.append('ီ');
                    } else if (c == 'ၧ') {
                        vowels.append('ံ');
                    } else if (c == 'ၥ') {
                        vowels.append('ိ');
                    }
                    kinzi = "ၤ";
                    break;
                case 6:
                    tones += NormalizeTones(c);
                    break;
                case 8:
                    if (c == '႒') {
                        mesials.append('\u103e');
                        vowels.append('ု');
                    }
                    break;
                case 7:
                    break;
                case 10:
                case 9:
                    boolean donesome = false;
                    if (consonant.length() > 1) {
                        if (kinzi.length() > 0) {
                            final_.append(consonant.charAt(0));
                            final_.append('င');
                            final_.append('\u103a');
                            final_.append('\u1039');
                            final_.append(consonant.charAt(1));
                            kinzi = "";
                        } else if (staker.length() > 0) {
                            final_.append(consonant.charAt(0));
                            final_.append(RegulateVowels(vowels.toString())); //just added
                            final_.append(consonant.charAt(1));
                            final_.append('\u1039');
                            final_.append(staker.charAt(0));
                            staker = "";
                        }
                        donesome = true;
                    } else if (consonant.length() > 0) {
                        final_.append(consonant);
                        donesome = true;
                    }
                    if (mesials.length() > 0) {
                        final_.append(RegulateMesials(mesials.toString()));
                    }
                    if (vowels.length() > 0) {
                        if (stacked) {
                            final_.append(RegulateVowels(svowels.toString()));
                        } else {
                            final_.append(RegulateVowels(vowels.toString()));
                        }
                    }
                    if (killer.length() > 0) {
                        if (killer.length() > 1) {
                            for (int r = 0; r < killer.length(); r++) {
                                char cx = killer.charAt(r);
                                final_.append(cx);
                                final_.append('\u103a');
                            }
                        } else {
                            final_.append(killer);
                            final_.append('\u103a');
                        }
                    }
                    if (tones.length() > 0) {
                        final_.append(tones);
                    }
                    consonant = "";
                    mesials = new StringBuffer();
                    vowels = new StringBuffer();
                    svowels = new StringBuffer();
                    stacked = false;
                    staker = "";
                    killer = "";
                    kinzi = "";
                    tones = "";
                    if (donesome) {
                        final_.append('\u200b');
                        donesome = false;
                    }
                    if (_case == 9) {
                        final_.append(c);
                    }
                    break;
            }
        }
        return final_.toString();
    }

    public static char NormalizeTones(char c) {
        if (c == 'း') {
            return c;
        } else {
            return '့';
        }
    }

    public static String NormalizeCon(char c) {
        if (c == 'ၙ') {
            return "န";
        } else {
            return String.valueOf(c);
        }
    }

    public static String NormalizeStackers(char c) {
        char[] sk = "ၠၡၢၣၨၩၪၫၴၵၷၸၹၺၻၼၽၾၿႌၮၯၰၱၲၳ".toCharArray();
        char[] sp = "ကခဂဃစဆဇဈဏတထဒဓနပဖဗဘမလဋဋဌဍဍဎ".toCharArray();
        for (int i = 0; i < sk.length; i++) {
            if (sk[i] == c) {
                return String.valueOf(sp[i]);
            }
        }
        return String.valueOf(c);
    }

    public static String RegulateVowels(String p) {
        char[] order = {'ေ', 'ိ', 'ီ', 'ဲ', 'ု', 'ူ', 'ံ', 'ာ', 'ါ', '်', 'ဿ'};
        return Regulate(order, p);
    }

    public static String RegulateMesials(String p) {
        char[] order = {'\u103b', '\u103c', '\u103d', '\u103e'};
        return Regulate(order, p);
    }

    public static String Regulate(char[] order, String p) {
        String s = "";
        for(int i=0;i<order.length;i++){
            char c = order[i];
            if(p.indexOf(c) > -1)
            {
                s += c;
            }
        }
        return s;
    }

    public static String NormalizeVowels(char c) {
        String s = String.valueOf(c);
        if (c == '႒') {
            s = "ု";
        } else if (c == 'ဳ') {
            s = "ု";
        } else if (c == 'ဴ') {
            s = "ူ";
        } else if (c == 'ဵ') {
            s = "ိံ";
        } else if (c == '႔' || c == '႕') {
            s = "့";
        } else if (c == 'ၗ') {
            return "\u103a";
        } else if (c == '\u108F') {
            return "ဿ";
        }
        return s;
    }

    public static String NormalizeMesials(char c) {
        String s;
        if (c == '\u1080') {
            s = "\u103b";
        } else if (c == '\u1081') {
            s = "\u103b\u103d";
        } else if (c == '\u1082') {
            s = "\u103b\u103e";
        } else if (c == '\u1083') {
            s = "\u103b\u103d\u103e";
        } else if (c == '\u1084' || c == '\u1085') {
            s = "\u103c";
        } else if (c == '\u108d') {
            s = "\u103d";
        } else if (c == '\u108e') {
            s = "\u103d\u103e";
        } else if (c == '\u1090' || c == '\u1091') {
            s = "\u103e";
        } else {
            s = String.valueOf(c);
        }
        return s;
    }
    public static int classReturner2(char ch) {
        if (ch == '\u200b') {
            return 10;
        }
        char[] c_consonants = "ကခဂဃငစဆဇဈဉညဋဌဍဎဏတထဒဓနၙပဖဗဘမယရလဝသဟဠအဣဤဥဦဧဩဪ၌၍၎၏".toCharArray();
        char[] c_attach = "ႀႁႂႃႍႎ႐႒ါာိီုူဲဳဴံ့းႏ၊။႑ဵ႔႕)".toCharArray();
        char[] c_vowel = "ေါာိီုူဲဳဴံဵ\u108f".toCharArray();
        char[] c_mesial = "ႀႁႂႃႄႅႍႎ႐႑".toCharArray();
        char[] c_fattach = "ႅႄႆႇႈႉႊႋေ".toCharArray();
        char[] c_killer = "်္ၗ".toCharArray();
        char[] c_stack = "ၠၡၢၣၨၩၪၫၴၵၷၸၹၺၻၼၽၾၿႌၮၯၰၱၲၳ".toCharArray();
        char[] c_kinzi = "ၤၥၦၧ".toCharArray();
        char[] c_tone = "့႔႕း".toCharArray();
        char[] c_ignore = "\u200d\u200c".toCharArray();
        if (exists(c_consonants, ch)) {
            return 0;
        } else if (exists(c_mesial, ch)) {
            return 1;
        } else if (exists(c_vowel, ch)) {
            return 2;
        } else if (exists(c_killer, ch)) {
            return 3;
        } else if (exists(c_stack, ch)) {
            return 4;
        } else if (exists(c_kinzi, ch)) {
            return 5;
        } else if (exists(c_tone, ch)) {
            return 6;
        } else if (exists(c_ignore, ch)) {
            return 7;
        } else if (ch == '႒') {
            return 8;
        }
        return 9;
    }

    public static int classReturner(char ch) {
        char[] c_consonants = "\u1000\u1001\u1002\u1003\u1004\u1005\u1006\u1007\u1008\u1009\u100A\u100B\u100C\u100D\u100E\u100F\u1010\u1011\u1012\u1013\u1014\u1059\u1015\u1016\u1017\u1018\u1019\u101A\u101B\u101C\u101D\u101E\u101F\u1020\u1021\u1023\u1024\u1025\u1026\u1027\u1029\u102A\u104C\u104D\u104E\u104F".toCharArray();
        char[] c_attach = "\u103b\u103d\u103e\u1057\u1060\u1061\u1062\u1063\u1068\u1069\u106A\u106B\u1074\u1075\u1077\u1078\u1079\u107A\u107B\u107C\u107D\u107E\u107F\u108C\u106E\u106F\u1070\u1071\u1072\u1073\u1080\u1081\u1082\u1083\u108D\u108E\u1090\u1092\u102B\u102C\u102D\u102E\u102F\u1030\u1032\u1033\u1034\u1036\u1037\u1038\u108F\u104A\u104B\u1091\u1035\u1094\u1095)".toCharArray();
        char[] c_fattach = "ြ\u1085\u1084\u1086\u1087\u1088\u1089\u108A\u108B\u1031".toCharArray();
        char[] c_killer = "\u103A".toCharArray();
        char[] c_stack = "\u1039".toCharArray();
        char[] c_kinzi = "\u1064\u1065\u1066\u1067".toCharArray();
        if (exists(c_consonants, ch)) {
            return 0;
        } else if (exists(c_attach, ch)) {
            return 1;
        } else if (exists(c_fattach, ch)) {
            return 2;
        } else if (exists(c_killer, ch)) {
            return 3;
        } else if (exists(c_stack, ch)) {
            return 4;
        } else if (exists(c_kinzi, ch)) {
            return 5;
        }
        return -1;
    }

    public static boolean exists(char[] ch_ar, char test) {
        for (int i = 0; i < ch_ar.length; i++) {
            if (test == ch_ar[i]) {
                return true;
            }
        }
        return false;
    }
}
