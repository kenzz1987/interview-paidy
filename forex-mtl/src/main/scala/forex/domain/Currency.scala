package forex.domain

import cats.Show

sealed trait Currency

object Currency {
  case object AED extends Currency; case object AFN extends Currency; case object ALL extends Currency
  case object AMD extends Currency; case object ANG extends Currency; case object AOA extends Currency
  case object ARS extends Currency; case object AUD extends Currency; case object AWG extends Currency
  case object AZN extends Currency; case object BAM extends Currency; case object BBD extends Currency
  case object BDT extends Currency; case object BGN extends Currency; case object BHD extends Currency
  case object BIF extends Currency; case object BMD extends Currency; case object BND extends Currency
  case object BOB extends Currency; case object BRL extends Currency; case object BSD extends Currency
  case object BTN extends Currency; case object BWP extends Currency; case object BYN extends Currency
  case object BZD extends Currency; case object CAD extends Currency; case object CDF extends Currency
  case object CHF extends Currency; case object CLP extends Currency; case object CNY extends Currency
  case object COP extends Currency; case object CRC extends Currency; case object CUC extends Currency
  case object CUP extends Currency; case object CVE extends Currency; case object CZK extends Currency
  case object DJF extends Currency; case object DKK extends Currency; case object DOP extends Currency
  case object DZD extends Currency; case object EGP extends Currency; case object ERN extends Currency
  case object ETB extends Currency; case object EUR extends Currency; case object FJD extends Currency
  case object FKP extends Currency; case object GBP extends Currency; case object GEL extends Currency
  case object GGP extends Currency; case object GHS extends Currency; case object GIP extends Currency
  case object GMD extends Currency; case object GNF extends Currency; case object GTQ extends Currency
  case object GYD extends Currency; case object HKD extends Currency; case object HNL extends Currency
  case object HRK extends Currency; case object HTG extends Currency; case object HUF extends Currency
  case object IDR extends Currency; case object ILS extends Currency; case object IMP extends Currency
  case object INR extends Currency; case object IQD extends Currency; case object IRR extends Currency
  case object ISK extends Currency; case object JEP extends Currency; case object JMD extends Currency
  case object JOD extends Currency; case object JPY extends Currency; case object KES extends Currency
  case object KGS extends Currency; case object KHR extends Currency; case object KMF extends Currency
  case object KPW extends Currency; case object KRW extends Currency; case object KWD extends Currency
  case object KYD extends Currency; case object KZT extends Currency; case object LAK extends Currency
  case object LBP extends Currency; case object LKR extends Currency; case object LRD extends Currency
  case object LSL extends Currency; case object LYD extends Currency; case object MAD extends Currency
  case object MDL extends Currency; case object MGA extends Currency; case object MKD extends Currency
  case object MMK extends Currency; case object MNT extends Currency; case object MOP extends Currency
  case object MRU extends Currency; case object MUR extends Currency; case object MVR extends Currency
  case object MWK extends Currency; case object MXN extends Currency; case object MYR extends Currency
  case object MZN extends Currency; case object NAD extends Currency; case object NGN extends Currency
  case object NIO extends Currency; case object NOK extends Currency; case object NPR extends Currency
  case object NZD extends Currency; case object OMR extends Currency; case object PAB extends Currency
  case object PEN extends Currency; case object PGK extends Currency; case object PHP extends Currency
  case object PKR extends Currency; case object PLN extends Currency; case object PYG extends Currency
  case object QAR extends Currency; case object RON extends Currency; case object RSD extends Currency
  case object RUB extends Currency; case object RWF extends Currency; case object SAR extends Currency
  case object SBD extends Currency; case object SCR extends Currency; case object SDG extends Currency
  case object SEK extends Currency; case object SGD extends Currency; case object SHP extends Currency
  case object SLL extends Currency; case object SOS extends Currency; case object SPL extends Currency
  case object SRD extends Currency; case object STN extends Currency; case object SVC extends Currency
  case object SYP extends Currency; case object SZL extends Currency; case object THB extends Currency
  case object TJS extends Currency; case object TMT extends Currency; case object TND extends Currency
  case object TOP extends Currency; case object TRY extends Currency; case object TTD extends Currency
  case object TVD extends Currency; case object TWD extends Currency; case object TZS extends Currency
  case object UAH extends Currency; case object UGX extends Currency; case object USD extends Currency
  case object UYU extends Currency; case object UZS extends Currency; case object VEF extends Currency
  case object VND extends Currency; case object VUV extends Currency; case object WST extends Currency
  case object XAF extends Currency; case object XCD extends Currency; case object XDR extends Currency
  case object XOF extends Currency; case object XPF extends Currency; case object YER extends Currency
  case object ZAR extends Currency; case object ZMW extends Currency; case object ZWD extends Currency
  case object Unknown extends Currency

  implicit val show: Show[Currency] = Show.show {
    case AED => "AED"; case AFN => "AFN"; case ALL => "ALL"; case AMD => "AMD"; case ANG => "ANG"
    case AOA => "AOA"; case ARS => "ARS"; case AUD => "AUD"; case AWG => "AWG"; case AZN => "AZN"
    case BAM => "BAM"; case BBD => "BBD"; case BDT => "BDT"; case BGN => "BGN"; case BHD => "BHD"
    case BIF => "BIF"; case BMD => "BMD"; case BND => "BND"; case BOB => "BOB"; case BRL => "BRL"
    case BSD => "BSD"; case BTN => "BTN"; case BWP => "BWP"; case BYN => "BYN"; case BZD => "BZD"
    case CAD => "CAD"; case CDF => "CDF"; case CHF => "CHF"; case CLP => "CLP"; case CNY => "CNY"
    case COP => "COP"; case CRC => "CRC"; case CUC => "CUC"; case CUP => "CUP"; case CVE => "CVE"
    case CZK => "CZK"; case DJF => "DJF"; case DKK => "DKK"; case DOP => "DOP"; case DZD => "DZD"
    case EGP => "EGP"; case ERN => "ERN"; case ETB => "ETB"; case EUR => "EUR"; case FJD => "FJD"
    case FKP => "FKP"; case GBP => "GBP"; case GEL => "GEL"; case GGP => "GGP"; case GHS => "GHS"
    case GIP => "GIP"; case GMD => "GMD"; case GNF => "GNF"; case GTQ => "GTQ"; case GYD => "GYD"
    case HKD => "HKD"; case HNL => "HNL"; case HRK => "HRK"; case HTG => "HTG"; case HUF => "HUF"
    case IDR => "IDR"; case ILS => "ILS"; case IMP => "IMP"; case INR => "INR"; case IQD => "IQD"
    case IRR => "IRR"; case ISK => "ISK"; case JEP => "JEP"; case JMD => "JMD"; case JOD => "JOD"
    case JPY => "JPY"; case KES => "KES"; case KGS => "KGS"; case KHR => "KHR"; case KMF => "KMF"
    case KPW => "KPW"; case KRW => "KRW"; case KWD => "KWD"; case KYD => "KYD"; case KZT => "KZT"
    case LAK => "LAK"; case LBP => "LBP"; case LKR => "LKR"; case LRD => "LRD"; case LSL => "LSL"
    case LYD => "LYD"; case MAD => "MAD"; case MDL => "MDL"; case MGA => "MGA"; case MKD => "MKD"
    case MMK => "MMK"; case MNT => "MNT"; case MOP => "MOP"; case MRU => "MRU"; case MUR => "MUR"
    case MVR => "MVR"; case MWK => "MWK"; case MXN => "MXN"; case MYR => "MYR"; case MZN => "MZN"
    case NAD => "NAD"; case NGN => "NGN"; case NIO => "NIO"; case NOK => "NOK"; case NPR => "NPR"
    case NZD => "NZD"; case OMR => "OMR"; case PAB => "PAB"; case PEN => "PEN"; case PGK => "PGK"
    case PHP => "PHP"; case PKR => "PKR"; case PLN => "PLN"; case PYG => "PYG"; case QAR => "QAR"
    case RON => "RON"; case RSD => "RSD"; case RUB => "RUB"; case RWF => "RWF"; case SAR => "SAR"
    case SBD => "SBD"; case SCR => "SCR"; case SDG => "SDG"; case SEK => "SEK"; case SGD => "SGD"
    case SHP => "SHP"; case SLL => "SLL"; case SOS => "SOS"; case SPL => "SPL"; case SRD => "SRD"
    case STN => "STN"; case SVC => "SVC"; case SYP => "SYP"; case SZL => "SZL"; case THB => "THB"
    case TJS => "TJS"; case TMT => "TMT"; case TND => "TND"; case TOP => "TOP"; case TRY => "TRY"
    case TTD => "TTD"; case TVD => "TVD"; case TWD => "TWD"; case TZS => "TZS"; case UAH => "UAH"
    case UGX => "UGX"; case USD => "USD"; case UYU => "UYU"; case UZS => "UZS"; case VEF => "VEF"
    case VND => "VND"; case VUV => "VUV"; case WST => "WST"; case XAF => "XAF"; case XCD => "XCD"
    case XDR => "XDR"; case XOF => "XOF"; case XPF => "XPF"; case YER => "YER"; case ZAR => "ZAR"
    case ZMW => "ZMW"; case ZWD => "ZWD"; case Unknown => "Unknown"
  }

  def fromString(s: String): Currency = s.toUpperCase match {
    case "AED" => AED; case "AFN" => AFN; case "ALL" => ALL; case "AMD" => AMD; case "ANG" => ANG
    case "AOA" => AOA; case "ARS" => ARS; case "AUD" => AUD; case "AWG" => AWG; case "AZN" => AZN
    case "BAM" => BAM; case "BBD" => BBD; case "BDT" => BDT; case "BGN" => BGN; case "BHD" => BHD
    case "BIF" => BIF; case "BMD" => BMD; case "BND" => BND; case "BOB" => BOB; case "BRL" => BRL
    case "BSD" => BSD; case "BTN" => BTN; case "BWP" => BWP; case "BYN" => BYN; case "BZD" => BZD
    case "CAD" => CAD; case "CDF" => CDF; case "CHF" => CHF; case "CLP" => CLP; case "CNY" => CNY
    case "COP" => COP; case "CRC" => CRC; case "CUC" => CUC; case "CUP" => CUP; case "CVE" => CVE
    case "CZK" => CZK; case "DJF" => DJF; case "DKK" => DKK; case "DOP" => DOP; case "DZD" => DZD
    case "EGP" => EGP; case "ERN" => ERN; case "ETB" => ETB; case "EUR" => EUR; case "FJD" => FJD
    case "FKP" => FKP; case "GBP" => GBP; case "GEL" => GEL; case "GGP" => GGP; case "GHS" => GHS
    case "GIP" => GIP; case "GMD" => GMD; case "GNF" => GNF; case "GTQ" => GTQ; case "GYD" => GYD
    case "HKD" => HKD; case "HNL" => HNL; case "HRK" => HRK; case "HTG" => HTG; case "HUF" => HUF
    case "IDR" => IDR; case "ILS" => ILS; case "IMP" => IMP; case "INR" => INR; case "IQD" => IQD
    case "IRR" => IRR; case "ISK" => ISK; case "JEP" => JEP; case "JMD" => JMD; case "JOD" => JOD
    case "JPY" => JPY; case "KES" => KES; case "KGS" => KGS; case "KHR" => KHR; case "KMF" => KMF
    case "KPW" => KPW; case "KRW" => KRW; case "KWD" => KWD; case "KYD" => KYD; case "KZT" => KZT
    case "LAK" => LAK; case "LBP" => LBP; case "LKR" => LKR; case "LRD" => LRD; case "LSL" => LSL
    case "LYD" => LYD; case "MAD" => MAD; case "MDL" => MDL; case "MGA" => MGA; case "MKD" => MKD
    case "MMK" => MMK; case "MNT" => MNT; case "MOP" => MOP; case "MRU" => MRU; case "MUR" => MUR
    case "MVR" => MVR; case "MWK" => MWK; case "MXN" => MXN; case "MYR" => MYR; case "MZN" => MZN
    case "NAD" => NAD; case "NGN" => NGN; case "NIO" => NIO; case "NOK" => NOK; case "NPR" => NPR
    case "NZD" => NZD; case "OMR" => OMR; case "PAB" => PAB; case "PEN" => PEN; case "PGK" => PGK
    case "PHP" => PHP; case "PKR" => PKR; case "PLN" => PLN; case "PYG" => PYG; case "QAR" => QAR
    case "RON" => RON; case "RSD" => RSD; case "RUB" => RUB; case "RWF" => RWF; case "SAR" => SAR
    case "SBD" => SBD; case "SCR" => SCR; case "SDG" => SDG; case "SEK" => SEK; case "SGD" => SGD
    case "SHP" => SHP; case "SLL" => SLL; case "SOS" => SOS; case "SPL" => SPL; case "SRD" => SRD
    case "STN" => STN; case "SVC" => SVC; case "SYP" => SYP; case "SZL" => SZL; case "THB" => THB
    case "TJS" => TJS; case "TMT" => TMT; case "TND" => TND; case "TOP" => TOP; case "TRY" => TRY
    case "TTD" => TTD; case "TVD" => TVD; case "TWD" => TWD; case "TZS" => TZS; case "UAH" => UAH
    case "UGX" => UGX; case "USD" => USD; case "UYU" => UYU; case "UZS" => UZS; case "VEF" => VEF
    case "VND" => VND; case "VUV" => VUV; case "WST" => WST; case "XAF" => XAF; case "XCD" => XCD
    case "XDR" => XDR; case "XOF" => XOF; case "XPF" => XPF; case "YER" => YER; case "ZAR" => ZAR
    case "ZMW" => ZMW; case "ZWD" => ZWD; case _ => Unknown
  }

  def allSupported: List[Currency] =
    List(
      AED, AFN, ALL, AMD, ANG, AOA, ARS, AUD, AWG, AZN, BAM, BBD, BDT, BGN, BHD, BIF, BMD, BND, BOB, BRL, BSD, BTN, BWP, BYN, BZD,
      CAD, CDF, CHF, CLP, CNY, COP, CRC, CUC, CUP, CVE, CZK, DJF, DKK, DOP, DZD, EGP, ERN, ETB, EUR, FJD, FKP, GBP, GEL, GGP, GHS,
      GIP, GMD, GNF, GTQ, GYD, HKD, HNL, HRK, HTG, HUF, IDR, ILS, IMP, INR, IQD, IRR, ISK, JEP, JMD, JOD, JPY, KES, KGS, KHR, KMF,
      KPW, KRW, KWD, KYD, KZT, LAK, LBP, LKR, LRD, LSL, LYD, MAD, MDL, MGA, MKD, MMK, MNT, MOP, MRU, MUR, MVR, MWK, MXN, MYR, MZN,
      NAD, NGN, NIO, NOK, NPR, NZD, OMR, PAB, PEN, PGK, PHP, PKR, PLN, PYG, QAR, RON, RSD, RUB, RWF, SAR, SBD, SCR, SDG, SEK, SGD,
      SHP, SLL, SOS, SPL, SRD, STN, SVC, SYP, SZL, THB, TJS, TMT, TND, TOP, TRY, TTD, TVD, TWD, TZS, UAH, UGX, USD, UYU, UZS, VEF,
      VND, VUV, WST, XAF, XCD, XDR, XOF, XPF, YER, ZAR, ZMW, ZWD
    )

  def allSupportedString: String =
    allSupported.map(_.toString).mkString(", ")

  def proxyPairsQueryString: String = {
    val allToUSD = allSupported.filterNot(_ == USD).map(c => s"${c.toString}USD").mkString("&pair=")
    val allFromUSD = allSupported.filterNot(_ == USD).map(c => s"USD${c.toString}").mkString("&pair=")
    s"$allToUSD&pair=$allFromUSD"
  }
}