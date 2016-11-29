/**
 * Created by Moses on 2016/10/26.
 */
function LENGTH_MEASURES() {
    this.mKilometer = 1000;
    this.mMeter = 1;
    this.mDecimeter = 0.1;
    this.mCentimeter = 0.01;
    this.mMillimeter = 0.001;
    this.mMicronmeter = 0.000001;
    this.mLimeter = 500;
    this.mZhangmeter = 10 / 3;
    this.mChimeter = 1 / 3;
    this.mCunmeter = 1 / 30;
    this.mFenmeter = 1 / 300;
    this.mmLimeter = 1 / 3000;
    this.engFoot = 0.3048;
    this.engMile = 5280 * this.engFoot;
    this.engFurlong = 660 * this.engFoot;
    this.engYard = 3 * this.engFoot;
    this.engInch = this.engFoot / 12;
    this.nautMile = 1852;
    this.nautFathom = 6 * this.engFoot;
}
var length_data = new LENGTH_MEASURES();
function POWER_MEASURES() {
    this.Watt = 0.001
    this.Kilowatt = 1
    this.Horsepower = 0.745712172
    this.mHorsepower = 0.7352941
    this.kgms = 0.0098039215
    this.kcals = 4.1841004
    this.Btus = 1.05507491
    this.ftlbs = 0.0013557483731
    this.Js = 0.001
    this.Nms = 0.001
}
var power_data = new POWER_MEASURES();
function AREA_MEASURES()
{
    this.mSquare_kilometer = (1000 * 1000)
    this.mHectare = (100 * 100)
    this.mSquare_meter = 1
    this.mAre = ((10000/15) * this.mSquare_meter)
    this.mSquare_decimeter = (0.1 * 0.1)
    this.mSquare_centimeter = (0.01 * 0.01)
    this.mSquare_millimeter = (0.001 * 0.001)
    this.engSquare_foot = (0.3048 * 0.3048)
    this.engSquare_yard = (3 * 3 * this.engSquare_foot)
    this.usSquare_rod = (16.5 *16.5 * this.engSquare_foot)
    this.engAcre = 160 * this.usSquare_rod
    this.engSquare_mile = (5280 *5280 * this.engSquare_foot)
    this.engSquare_inch = (this.engSquare_foot / (12 * 12))
}
var area_data = new AREA_MEASURES();

function VOL_MEASURES() {
    this.mCubic_meter = 1000
    this.mHectoliter = 100
    this.mDekaliter = 10
    this.mLiter = 1
    this.mDeciliter = 0.1
    this.mCentiliter = 0.01
    this.mMilliliter = 0.001
    this.mCubic_millimeter = 0.000001
    this.mcTable_spoon= 0.015
    this.mcTea_spoon= 0.005
    this.uscCubic_inch = 0.016387064
    this.uscAcre_foot = 43560 * 1728 * this.uscCubic_inch
    this.uscCubic_yard = 27 * 1728 * this.uscCubic_inch
    this.uscCubic_foot = 1728 * this.uscCubic_inch
    this.uslGallon = 231 * this.uscCubic_inch
    this.uslBarrel = 42 * this.uslGallon
    this.uslQuart =  this.uslGallon / 4
    this.uslPint =  this.uslGallon / 8
    this.uslGill =  this.uslGallon / 32
    this.uslFluid_ounce = this.uslGallon / 128
    this.uslFluid_dram =  this.uslGallon / 1024
    this.uslMinim = this.uslFluid_ounce / 61440
    this.usdBarrel = 7056 * this.uscCubic_inch
    this.usdBushel = 2150.42 * this.uscCubic_inch
    this.usdPeck = this.usdBushel / 4
    this.usdQuart = this.usdBushel / 32
    this.usdPint = this.usdBushel / 64
    this.uscCup = 8 * this.uslFluid_ounce
    this.uscTable_spoon = this.uslFluid_ounce / 2
    this.uscTea_spoon = this.uslFluid_ounce / 6
    this.briGallon = 4.54609
    this.briBarrel = 36 * this.briGallon
    this.briBushel = 8  * this.briGallon
    this.briPint = this.briGallon / 8
    this.briFluid_ounce = this.briGallon / 160
}
var vol_data = new VOL_MEASURES();
function HEAT_MEASURES() {
    this.Joule = 1
    this.Kgm = 9.80392157
    this.Psh = 2647603.9184538
    this.Hph = 2684563.7583893
    this.Kwh = 3599712.023038157
    this.Kcal = 4185.851820846
    this.Btu = 1055.0749103
    this.Ftlb = 1.3557483731
}
var heat_data = new HEAT_MEASURES();
function WEIGHT_MEASURES() {
    this.mTon = 1000
    this.mKilogram = 1
    this.mGram = 0.001
    this.mMilligram = 0.000001
    this.cJin = 0.5
    this.cDan = 50
    this.cLiang = 0.05
    this.cQian = 0.005
    this.avdpPound = 0.45359237
    this.briTon = 2240 * this.avdpPound
    this.usTon = 2000 * this.avdpPound
    this.briCWT = 112 * this.avdpPound
    this.usCWT = 100 * this.avdpPound
    this.briStone = 14 * this.avdpPound
    this.avdpOunce = this.avdpPound / 16
    this.avdpDram= this.avdpPound / 256
    this.avdpGrain = this.avdpPound / 7000
    this.troyPound = 5760 * this.avdpGrain
    this.troyOunce = 480 * this.avdpGrain
    this.troyDWT = 24 * this.avdpGrain
    this.troyGrain = this.avdpGrain
}
var weight_data = new WEIGHT_MEASURES();

function PRESS_MEASURES() {
    this.mKilopascal = 1000
    this.mHectopascal = 100
    this.mPascal = 1
    this.mBar = 100000
    this.mMillibar = 100
    this.mAtm = 101325
    this.mMillimeter_Hg = this.mAtm / 760
    this.engInch_Hg = 25.4 * this.mMillimeter_Hg
    this.engPound_sq_inch = 6894.757
    this.engPound_sq_foot = this.engPound_sq_inch / 144
    this.xpressKg_sq_cm = 98066.5
    this.xpressKg_sq_m = 9.80665
    this.mmmH2O = 1/0.101972
}
var press_data = new PRESS_MEASURES();
function computeTempC(obj) {
    var tempC = parseFloat(obj.tempCelsius.value)
    if( (tempC >= 0) && (obj.tempCelsius.value.indexOf("-") != -1) ) {
        tempC = -tempC;
    }
    if(!(tempC < -273.15) ) {
        var tempK = tempC + 273.15
        var tempF = 32 + (tempC * 9 / 5)
        var tempRa = tempK*1.8
        var tempRe = tempC/1.25
        obj.tempKelvin.value = tempK
        obj.tempFahr.value = tempF
        obj.tempRankine.value = tempRa
        obj.tempReaumur.value = tempRe
    } else {
        obj.tempKelvin.value = "ERROR"
        obj.tempFahr.value = "ERROR"
        obj.tempRankine.value = "ERROR"
        obj.tempReaumur.value = "ERROR"
    }
}
function computeTempF(obj) {
    var tempF = parseFloat(obj.tempFahr.value)
    if( (tempF >= 0) && (obj.tempFahr.value.indexOf("-") != -1) ) tempF = -tempF;
    if(!(tempF < -459.666666) ) {
        var tempC = (tempF - 32) * 5 / 9
        var tempK = tempC + 273.15
        var tempRa = tempK*1.8
        var tempRe = tempC/1.25
        obj.tempCelsius.value = tempC
        obj.tempKelvin.value = tempK
        obj.tempRankine.value = tempRa
        obj.tempReaumur.value = tempRe
    } else  {
        obj.tempCelsius.value = "ERROR"
        obj.tempKelvin.value = "ERROR"
        obj.tempRankine.value = "ERROR"
        obj.tempReaumur.value = "ERROR"
    }
}
function computeTempK(obj) {
    var tempK = parseFloat(obj.tempKelvin.value)
    if( (tempK >= 0) && (obj.tempKelvin.value.indexOf("-") != -1) ) {
        tempK = -tempK;
    }
    if(!(tempK < 0) ) {
        var tempC = tempK - 273.15
        var tempF = 32 + (tempC * 9 / 5)
        var tempRa = tempK*1.8
        var tempRe = tempC/1.25
        obj.tempCelsius.value = tempC
        obj.tempFahr.value = tempF
        obj.tempRankine.value = tempRa
        obj.tempReaumur.value = tempRe
    } else {
        obj.tempCelsius.value = "ERROR"
        obj.tempFahr.value = "ERROR"
        obj.tempRankine.value = "ERROR"
        obj.tempReaumur.value = "ERROR"
    }
}
function computeTempRa(obj) {
    var tempRa = parseFloat(obj.tempRankine.value)
    if( (tempRa >= 0) && (obj.tempRankine.value.indexOf("-") != -1) ) {
        tempRa = -tempRa;
    }
    if(!(tempRa < 0) ) {
        var tempK = tempRa/1.8
        var tempC = tempK  - 273.15
        var tempF = 32 + (tempC * 9 / 5)
        var tempRe = tempC/1.25
        obj.tempCelsius.value = tempC
        obj.tempFahr.value = tempF
        obj.tempKelvin.value = tempK
        obj.tempReaumur.value = tempRe
    } else {
        obj.tempCelsius.value = "ERROR"
        obj.tempFahr.value = "ERROR"
        obj.tempKelvin.value = "ERROR"
        obj.tempReaumur.value = "ERROR"
    }
}
function computeTempRe(obj) {
    var tempRe = parseFloat(obj.tempReaumur.value)
    if( (tempRe >= 0) && (obj.tempReaumur.value.indexOf("-") != -1) ) {
        tempRe = -tempRe;
    }
    if(!(tempRe < -218.5199999999) ) {
        var tempC = tempRe*1.25
        var tempK = tempC + 273.15
        var tempF = 32 + (tempC * 9 / 5)
        var tempRa = tempK*1.8
        obj.tempCelsius.value = tempC
        obj.tempFahr.value = tempF
        obj.tempKelvin.value = tempK
        obj.tempRankine.value = tempRa
    } else {
        obj.tempCelsius.value = "ERROR"
        obj.tempFahr.value = "ERROR"
        obj.tempKelvin.value = "ERROR"
        obj.tempRankine.value = "ERROR"
    }
}

function checkNum(str) {
    for (var i=0; i<str.length; i++)
    {
        var ch = str.substring(i, i + 1)
        if (ch!="." && ch!="+" && ch!="-" && ch!="e" && ch!="E" && (ch < "0" || ch > "9"))
        {
            alert("请输入有效的数字");
            return false;
        }
    }
    return true;
}
function normalize(what,digits) {
    var str=""+what;
    var pp=Math.max(str.lastIndexOf("+"),str.lastIndexOf("-"));
    var idot=str.indexOf(".");
    if (idot>=1)
    {
        var ee=(pp>0)?str.substring(pp-1,str.length):"";
        digits+=idot;
        if (digits>=str.length)
            return str;
        if (pp>0 && digits>=pp)
            digits-=pp;
        var c=eval(str.charAt(digits));
        var ipos=digits-1;
        if (c>=5)
        {
            while (str.charAt(ipos)=="9")
                ipos--;
            if (str.charAt(ipos)==".")
            {
                var nc=eval(str.substring(0,idot))+1;
                if (nc==10 && ee.length>0)
                {
                    nc=1;
                    ee="e"+(eval(ee.substring(1,ee.length))+1);
                }
                return ""+nc+ee;
            }
            return str.substring(0,ipos)+(eval(str.charAt(ipos))+1)+ee;
        }
        else
            var ret=str.substring(0,digits)+ee;
        for (var i=0; i<ret.length; i++)
            if (ret.charAt(i)>"0" && ret.charAt(i)<="9")
                return ret;
        return str;
    }
    return str;
}
function compute(obj,val,data) {
    if (obj[val].value)
    {
        var uval=0;
        uval = obj[val].value*data[val];
        if( (uval >= 0) && (obj[val].value.indexOf("-") != -1) )
        {
            uval = -uval;// *** Hack for Opera 4.0  2000-10-14
        }
        for (var i in data)
            obj[i].value=normalize(uval/data[i],8);
    }
}
function resetValues(form,data) {
    for (var i in data)
        form[i].value="";
}
function resetAll(form) {
    resetValues(form,length_data);
}