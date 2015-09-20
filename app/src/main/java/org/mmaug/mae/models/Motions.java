package org.mmaug.mae.models;

import java.io.Serializable;

/**
 * _id: "55fdb18d71fd5fec3094227a",
 * submitted_date: "25-Oct-12",
 * house: "First Amyotha Hluttaw",
 * session: "Fifth Regular Session",
 * description: {
 * english: "Changing the procedure of Education for more open school",
 * myanmar: "တိုင်းဒေသကြီး သို့မဟုတ် ပြည်နယ်များအတွင်းရှိ မြို့နယ်များ၏ကျေးရွာများတွင်
 * ဖွင့်လှစ်သင်ကြားလျက်ရှိသော အခြေခံပညာကျောင်းများအား အဆင့်တိုးမြှင့် ဖွင့်လှစ်ခွင့်ရရှိရေး အဆိုပြု
 * တင်ပြချက်များနှင့် ပတ်သက်၍ ပညာရေးဌာနဆိုင်ရာ လုပ်ထုံးလုပ်နည်း စည်းမျဉ်း စည်းကမ်းများအား
 * လျှော့ပေါ့ပြင်ဆင်ပေးရန် ပြည်ထောင်စုအစိုးရအား တိုက်တွန်းကြောင်း အဆို"
 * },
 * issue: "Education",
 * purpose: "Scrutiny",
 * status: "Approved",
 * response_date: "23-Nov-12",
 * respondent: {
 * name: "U Aye Q",
 * position: "Deputy Education Minister"
 * },
 * mpid: "UPMP-01-0142",
 * motions: "UP-01-05-007
 * Created by indexer on 9/20/15.
 */
public class Motions implements Serializable {
  public String _id;
  public String submitted_date;
  public String house;
  public String session;
  public Description description;
  public String issue;
  public String purpose;
  public String status;
}
