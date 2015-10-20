package org.mmaug.mae.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by poepoe on 15/9/15.
 */
public class DataUtils {

  private static DataUtils instance;
  private Context mContext;
  private Gson gson = new Gson();

  public DataUtils(Context context) {
    this.mContext = context;
  }

  public DataUtils() {

  }

  public static DataUtils getInstance(Context mContext) {
    if (instance == null) {
      instance = new DataUtils(mContext);
    }
    return instance;
  }

  public ArrayList<Township> loadTownship() {
    ArrayList<Township> townships = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Township.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        Township township = gson.fromJson(element, Township.class);
        townships.add(township);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return townships;
  }

  private ArrayList<VillageOrWard> loadVWByTownship(String tspCode) {
    ArrayList<VillageOrWard> villageOrWardsByTownship = new ArrayList<>();
    ArrayList<VillageOrWard> villageOrWards = loadWV();

    for (VillageOrWard villageOrWard : villageOrWards) {
      if (villageOrWard.TSPcode.equalsIgnoreCase(tspCode)) {
        villageOrWardsByTownship.add(villageOrWard);
      }
    }
    return villageOrWardsByTownship;
  }

  public ArrayList<VillageOrWard> loadWV() {
    ArrayList<VillageOrWard> villageOrWards = new ArrayList<>();
    ArrayList<Ward> wards = loadWard();
    ArrayList<Village> villages = loadVillage();
    for (Ward ward : wards) {
      VillageOrWard villageOrWard = new VillageOrWard();
      villageOrWard.TSPcode = ward.getTSPcode();
      villageOrWard.VWcode = ward.WardPcode;
      villageOrWard.VWName = ward.getWard();
      villageOrWard.VWNameBurmese = ward.getWardNameBurmese();
      villageOrWards.add(villageOrWard);
    }

    for (Village village : villages) {
      VillageOrWard villageOrWard = new VillageOrWard();
      villageOrWard.TSPcode = village.getTSPcode();
      villageOrWard.VWcode = village.getVTPcode();
      villageOrWard.VWName = village.getVillageTract();
      villageOrWard.VWNameBurmese = village.getVillageTractMyaMMR3();
      villageOrWards.add(villageOrWard);
    }

    return villageOrWards;
  }

  public ArrayList<Ward> loadWard() {
    ArrayList<Ward> wards = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Wards.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        Ward ward = gson.fromJson(element, Ward.class);
        wards.add(ward);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return wards;
  }

  public ArrayList<Village> loadVillage() {
    ArrayList<Village> villages = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Village Tracts.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        Village village = gson.fromJson(element, Village.class);
        villages.add(village);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return villages;
  }

  public static class Township {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("D_Pcode") private String DPcode;
    @SerializedName("District") private String District;
    @SerializedName("TS_Pcode") private String TSPcode;
    @SerializedName("Township") private String TownshipName;
    @SerializedName("Township_Mya_MM3") private String TowhshipNameBurmese;
    @SerializedName("MYAINFO_TS_ID") private String MyanInfoTsId;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrict() {
      return District;
    }

    public void setDistrict(String district) {
      District = district;
    }

    public String getTSPcode() {
      return TSPcode;
    }

    public void setTSPcode(String TSPcode) {
      this.TSPcode = TSPcode;
    }

    public String getTownshipName() {
      return TownshipName;
    }

    public void setTownshipName(String townshipName) {
      TownshipName = townshipName;
    }

    public String getTowhshipNameBurmese() {
      return TowhshipNameBurmese;
    }

    public void setTowhshipNameBurmese(String towhshipNameBurmese) {
      TowhshipNameBurmese = towhshipNameBurmese;
    }

    public String getMyanInfoTsId() {
      return MyanInfoTsId;
    }

    public void setMyanInfoTsId(String myanInfoTsId) {
      MyanInfoTsId = myanInfoTsId;
    }
  }

  public static class Ward {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("D_Pcode") private String DPcode;
    @SerializedName("District") private String District;
    @SerializedName("TS_Pcode") private String TSPcode;
    @SerializedName("Township") private String TownshipName;
    @SerializedName("Town_Pcode") private String TownPcode;
    @SerializedName("Town") private String TownName;
    @SerializedName("Ward_Pcode") private String WardPcode;
    @SerializedName("Ward") private String Ward;
    @SerializedName("Ward_Mya_MM3") private String WardNameBurmese;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrict() {
      return District;
    }

    public void setDistrict(String district) {
      District = district;
    }

    public String getTSPcode() {
      return TSPcode;
    }

    public void setTSPcode(String TSPcode) {
      this.TSPcode = TSPcode;
    }

    public String getTownshipName() {
      return TownshipName;
    }

    public void setTownshipName(String townshipName) {
      TownshipName = townshipName;
    }

    public String getTownPcode() {
      return TownPcode;
    }

    public void setTownPcode(String townPcode) {
      TownPcode = townPcode;
    }

    public String getTownName() {
      return TownName;
    }

    public void setTownName(String townName) {
      TownName = townName;
    }

    public String getWard() {
      return Ward;
    }

    public void setWard(String ward) {
      Ward = ward;
    }

    public String getWardPcode() {
      return WardPcode;
    }

    public void setWardPcode(String wardPcode) {
      WardPcode = wardPcode;
    }

    public String getWardNameBurmese() {
      return WardNameBurmese;
    }

    public void setWardNameBurmese(String wardNameBurmese) {
      WardNameBurmese = wardNameBurmese;
    }
  }

  public static class Village {

    @SerializedName("SR_Pcode") @Expose private String SRPcode;
    @SerializedName("State_Region") @Expose private String StateRegion;
    @SerializedName("D_Pcode") @Expose private String DPcode;
    @SerializedName("District") @Expose private String District;
    @SerializedName("TS_Pcode") @Expose private String TSPcode;
    @SerializedName("Township") @Expose private String Township;
    @SerializedName("VT_Pcode") @Expose private String VTPcode;
    @SerializedName("Village_Tract") @Expose private String VillageTract;
    @SerializedName("Village_Tract_Mya_MMR3") @Expose private String VillageTractMyaMMR3;
    @SerializedName("Description") @Expose private String Description;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getStateRegion() {
      return StateRegion;
    }

    public void setStateRegion(String StateRegion) {
      this.StateRegion = StateRegion;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrict() {
      return District;
    }

    public void setDistrict(String District) {
      this.District = District;
    }

    public String getTSPcode() {
      return TSPcode;
    }

    public void setTSPcode(String TSPcode) {
      this.TSPcode = TSPcode;
    }

    public String getTownship() {
      return Township;
    }

    public void setTownship(String Township) {
      this.Township = Township;
    }

    public String getVTPcode() {
      return VTPcode;
    }

    public void setVTPcode(String VTPcode) {
      this.VTPcode = VTPcode;
    }

    public String getVillageTract() {
      return VillageTract;
    }

    public void setVillageTract(String VillageTract) {
      this.VillageTract = VillageTract;
    }

    public String getVillageTractMyaMMR3() {
      return VillageTractMyaMMR3;
    }

    public void setVillageTractMyaMMR3(String VillageTractMyaMMR3) {
      this.VillageTractMyaMMR3 = VillageTractMyaMMR3;
    }

    public String getDescription() {
      return Description;
    }

    public void setDescription(String Description) {
      this.Description = Description;
    }
  }

  private static class VillageOrWard {
    private String TSPcode;
    private String VWcode;
    private String VWName;
    private String VWNameBurmese;
  }
}
