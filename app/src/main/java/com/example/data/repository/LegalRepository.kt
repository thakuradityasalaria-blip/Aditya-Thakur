package com.example.data.repository

import com.example.data.model.ChecklistItem
import com.example.data.model.DivorceGround
import com.example.data.model.GroundCategory
import com.example.data.model.Lawyer
import com.example.data.model.LegalFaq
import com.example.data.model.ProcedureStep

object LegalRepository {

  val divorceGrounds: List<DivorceGround> = listOf(
    DivorceGround(
      id = "mutual_consent",
      title = "Mutual Consent Divorce",
      category = GroundCategory.MUTUAL,
      actAndSection = "Section 13B (Hindu Marriage Act, 1955) / Sec 28 (Special Marriage Act)",
      shortDescription = "Both spouses mutually agree that the marriage has broken down and have settled all terms regarding alimony, custody, and assets.",
      detailedExplanation = "Mutual consent is the swiftest and most amicable legal route for dissolving a marriage in India. Both parties jointly file a petition stating that they have been living separately for a period of one year or more, that they have not been able to live together, and that they have mutually agreed that the marriage should be dissolved.",
      keyRequirements = listOf(
        "Living separately for at least 1 continuous year prior to filing",
        "Free consent without coercion, fraud, or undue influence",
        "Complete settlement of permanent alimony/maintenance",
        "Mutual agreement on child custody and visitation rights",
        "Mutual agreement on division of shared assets and return of Stridhan"
      ),
      requiredEvidence = listOf(
        "Original Marriage Certificate or Wedding Card with photos",
        "Joint petition signed by both husband and wife with identity proofs",
        "Proof of living separately (separate address proofs/rental agreements)",
        "Memorandum of Settlement / MoU detailing financial and custody terms"
      ),
      statutoryStatus = "Fastest & Most Recommended Route (3 to 6 months)",
      isIllegalWarning = false,
      practicalTips = "Under Supreme Court judgment in Amardeep Singh v. Harveen Kaur (2017), the statutory 6-month cooling-off period between First and Second Motion can be waived if all mediation and settlements are complete."
    ),

    DivorceGround(
      id = "triple_talaq_legal_ban",
      title = "Triple Talaq (Talaq-e-Biddat) - Strict Legal Ban & Protection",
      category = GroundCategory.MUSLIM_LAW,
      actAndSection = "The Muslim Women (Protection of Rights on Marriage) Act, 2019",
      shortDescription = "Instantaneous Triple Talaq is void, illegal, and a non-bailable criminal offense. The app educates users on this prohibition and legal protections.",
      detailedExplanation = "Under Section 3 of the 2019 Act, any pronouncement of Talaq-e-Biddat (instant triple talaq pronounced in words, spoken, written, or electronic form like SMS or WhatsApp) by a Muslim husband upon his wife is completely VOID and ILLEGAL. It does not terminate the marriage. Furthermore, Section 4 prescribes imprisonment for up to 3 years and a fine for the husband. The wife is statutorily entitled to a subsistence allowance and custody of minor children.",
      keyRequirements = listOf(
        "Instant triple talaq has NO legal effect in India; the marriage remains legally intact",
        "Husband is subject to criminal prosecution under Section 4 (up to 3 years imprisonment)",
        "Aggrieved woman is entitled to subsistence allowance for herself and dependent children (Sec 5)",
        "Aggrieved woman is entitled to custody of minor children (Sec 6)",
        "Police cannot grant bail without hearing the aggrieved woman"
      ),
      requiredEvidence = listOf(
        "Recording, SMS, WhatsApp message, written letter, or affidavit containing triple talaq pronouncement",
        "Witness statements if pronounced verbally in presence of relatives or arbiters",
        "Nikahnama (Marriage Contract) copy",
        "Application before Judicial Magistrate of the First Class"
      ),
      statutoryStatus = "Illegal & Void in Law (Criminal Offence)",
      isIllegalWarning = true,
      warningNote = "CRITICAL LEGAL NOTICE: Instant Triple Talaq is NOT a valid method of divorce. If pronounced, report to legal counsel or Magistrate under the 2019 Act for immediate protection, allowance, and custody.",
      practicalTips = "Valid avenues for Muslim dissolution are Talaq-e-Sunnah (Ahsan/Hasan over months), Khula (initiated by wife), Mubarat (mutual consent), or court decree under Dissolution of Muslim Marriages Act 1939."
    ),

    DivorceGround(
      id = "muslim_wife_khula_mubarat",
      title = "Khula & Mubarat (Dissolution under Muslim Law)",
      category = GroundCategory.MUSLIM_LAW,
      actAndSection = "Muslim Personal Law (Shariat) Application Act & Kerala HC Judgment (2021)",
      shortDescription = "Wife's right to initiate divorce (Khula) or mutual separation (Mubarat) without undergoing fault-based litigation.",
      detailedExplanation = "In Khula, the wife initiates dissolution of marriage by offering to return the dower (Mahr) or waive certain financial claims. The Kerala High Court (2021) reaffirmed that Muslim women possess an unconditional right to invoke Khula even without the husband's agreement, subject to fair reconciliation efforts. Mubarat represents mutual dissolution where both parties desire separation.",
      keyRequirements = listOf(
        "Clear desire and declaration by the wife to terminate the marital bond",
        "Offer to return part or all of Mahr (dower) or financial consideration",
        "Reconciliation attempt through family elders or community mediator",
        "Observance of Iddat period (approx. three menstrual cycles or three months)"
      ),
      requiredEvidence = listOf(
        "Written Khula notice / Khulanama signed by wife",
        "Nikahnama specifying initial Mahr agreed upon",
        "Proof of offer/return of Mahr or settlement receipt",
        "Declaration before Family Court for declaratory decree"
      ),
      statutoryStatus = "Statutory & Judicial Right of Muslim Women",
      isIllegalWarning = false,
      practicalTips = "A declaratory suit in Family Court is advisable to obtain a formal decree affirming the Khula dissolution for government records, passports, and remarriage validity."
    ),

    DivorceGround(
      id = "dmma_judicial_divorce",
      title = "Muslim Women Judicial Divorce (DMMA 1939)",
      category = GroundCategory.MUSLIM_LAW,
      actAndSection = "Dissolution of Muslim Marriages Act, 1939 (Section 2)",
      shortDescription = "Statutory grounds on which a Muslim woman can file for judicial divorce in Family Court against her husband.",
      detailedExplanation = "The DMMA 1939 provides nine specific statutory grounds enabling Muslim wives to petition the Family Court for dissolution of marriage independently.",
      keyRequirements = listOf(
        "Husband's whereabouts unknown for a period of 4 years",
        "Husband has neglected or failed to provide maintenance for 2 years",
        "Husband sentenced to imprisonment for 7 years or more",
        "Husband failed to perform marital obligations for 3 years without reasonable cause",
        "Husband was impotent at the time of marriage and continues to be so",
        "Husband insane for 2 years or suffering from virulent venereal disease",
        "Cruelty (habitual assault, associating with evil persons, forcing immoral life, disposing of her property)",
        "Repudiation of marriage (Option of Puberty: married before age 18)"
      ),
      requiredEvidence = listOf(
        "Nikahnama and proof of residence",
        "Police missing report / gazette notices (if husband missing 4 years)",
        "Bank statements showing zero maintenance received for 2 years",
        "Medical certificates / witness testimony for cruelty or health grounds",
        "Criminal conviction copy (if imprisoned for 7+ years)"
      ),
      statutoryStatus = "Statutory Judicial Remedy in Family Court",
      isIllegalWarning = false,
      practicalTips = "Muslim women filing under DMMA 1939 can simultaneously claim interim maintenance under Section 125 CrPC / Section 144 BNSS in Family Court."
    ),

    DivorceGround(
      id = "cruelty_physical_mental",
      title = "Cruelty (Physical & Mental Cruelty)",
      category = GroundCategory.HINDU_MARRIAGE,
      actAndSection = "Section 13(1)(ia) - Hindu Marriage Act, 1955 & Special Marriage Act",
      shortDescription = "Conduct of such a character as to cause reasonable apprehension in mind that it will be harmful or injurious to live with the spouse.",
      detailedExplanation = "Cruelty includes both physical violence and mental torture. Mental cruelty comprises persistent humiliation, false criminal allegations, denying food or medical aid, constant dowry demands, abusive language, deprivation of conjugal relations for extended periods, or subjecting the spouse to public disgrace.",
      keyRequirements = listOf(
        "Severity that renders continuous marital living intolerable or hazardous",
        "Not ordinary wear and tear or minor domestic disputes of married life",
        "Cumulative conduct creating grave apprehension of physical or psychological harm",
        "Must not be condoned (reconciled and forgiven with resumption of cohabitation)"
      ),
      requiredEvidence = listOf(
        "Medical records, injury reports (MLC), or hospital admission slips",
        "Police complaints (NCs, PCR calls, complaints under Domestic Violence Act)",
        "Audio/video recordings, abusive SMS/WhatsApp chats, emails",
        "Witness statements from neighbors, counselors, or relatives"
      ),
      statutoryStatus = "Most Common Contested Ground",
      isIllegalWarning = false,
      practicalTips = "Courts assess mental cruelty based on social status, educational background, and cumulative behavior rather than an isolated incident."
    ),

    DivorceGround(
      id = "desertion",
      title = "Desertion (Abandonment for 2+ Years)",
      category = GroundCategory.HINDU_MARRIAGE,
      actAndSection = "Section 13(1)(ib) - Hindu Marriage Act, 1955",
      shortDescription = "Intentional permanent abandonment of one spouse by the other without reasonable cause and without consent.",
      detailedExplanation = "Desertion requires two elements: the factum of separation (physical living apart) and the animus deserendi (the intention to abandon the marital obligation permanently). It must continue for an uninterrupted period of at least two years immediately preceding the petition presentation.",
      keyRequirements = listOf(
        "Continuous separation of at least 2 full years prior to petition filing",
        "Absence of consent from the abandoned spouse",
        "Absence of reasonable cause on the part of the abandoning spouse",
        "Animus deserendi (deliberate intention to abandon marital ties)"
      ),
      requiredEvidence = listOf(
        "Proof of separate residences (utility bills, rental agreements)",
        "Legal notice requesting return / resumption of conjugal rights with no response",
        "Communication logs showing persistent refusal to return",
        "Affidavit confirming 2+ years uninterrupted separation"
      ),
      statutoryStatus = "Contested Fault-Based Ground",
      isIllegalWarning = false,
      practicalTips = "If the petitioner forced the other spouse out through cruelty, the petitioner cannot claim desertion (doctrine of constructive desertion)."
    ),

    DivorceGround(
      id = "adultery",
      title = "Adultery / Extramarital Relationship",
      category = GroundCategory.HINDU_MARRIAGE,
      actAndSection = "Section 13(1)(i) - Hindu Marriage Act, 1955 & Special Marriage Act",
      shortDescription = "Voluntary sexual intercourse with any person other than his or her spouse after solemnization of marriage.",
      detailedExplanation = "While adultery was decriminalized by the Supreme Court in Joseph Shine v. Union of India (2018), it remains a primary and valid civil ground for divorce. Direct ocular proof is not mandatory; circumstantial evidence leading to an irresistible inference of infidelity is sufficient.",
      keyRequirements = listOf(
        "Voluntary sexual intimacy outside marriage",
        "Substantiated by circumstantial evidence or admissions",
        "The third party (paramour) is often required to be made a co-respondent in the petition"
      ),
      requiredEvidence = listOf(
        "Hotel stay invoices, travel itineraries with third party",
        "Photographs, intimate chat logs, messages acknowledging relationship",
        "Call Detail Records (CDR) showing continuous odd-hour calls",
        "Admissions made during family counseling or mediation"
      ),
      statutoryStatus = "Civil Fault Ground for Divorce",
      isIllegalWarning = false,
      practicalTips = "Evidence must be collected legally without violating constitutional privacy norms or hacking private protected accounts."
    ),

    DivorceGround(
      id = "irretrievable_breakdown",
      title = "Irretrievable Breakdown of Marriage (Art. 142)",
      category = GroundCategory.MUTUAL,
      actAndSection = "Article 142 of Constitution of India (Supreme Court Constitutional Bench, 2023)",
      shortDescription = "When the marital relationship has completely deadlocked with zero possibility of emotional reunion, Supreme Court can dissolve the union directly.",
      detailedExplanation = "In the landmark Constitution Bench ruling Shilpa Sailesh v. Varun Sreenivasan (2023), the Supreme Court ruled that it has the power under Article 142 to grant divorce on the ground of 'irretrievable breakdown of marriage', even if one spouse objects, provided all efforts at reconciliation have failed and the marriage is practically dead.",
      keyRequirements = listOf(
        "Extended period of physical separation (typically 5+ years)",
        "Multiple failed mediation attempts before court-appointed mediators",
        "Irreparable emotional deadlock with mutual bitterness",
        "Adequate and just financial provision made for the dependent spouse and children"
      ),
      requiredEvidence = listOf(
        "Mediation failure reports from Family Court / High Court mediation centers",
        "Timeline of prolonged separation without cohabitation",
        "Complete asset and income disclosures as per Rajnesh v. Neha formula",
        "Documentation of past litigation history showing no prospect of reunion"
      ),
      statutoryStatus = "Extraordinary Power of Supreme Court",
      isIllegalWarning = false,
      practicalTips = "Family courts cannot grant this ground on their own (as it is not yet in statute), but petitions under Article 142 can be moved directly before the Supreme Court."
    ),

    DivorceGround(
      id = "special_marriage_act",
      title = "Special Marriage Act (Civil & Interfaith Grounds)",
      category = GroundCategory.SPECIAL_MARRIAGE,
      actAndSection = "Section 27 & Section 28 - Special Marriage Act, 1954",
      shortDescription = "For marriages registered under civil law regardless of religion, offering both mutual consent and fault grounds.",
      detailedExplanation = "Applicable to all citizens who solemnize or register their marriage under the Special Marriage Act. Section 27 offers fault grounds (cruelty, desertion, adultery, imprisonment for 7+ years, failure to comply with restitution decree), and Section 28 provides mutual consent with a 1-year separation requirement.",
      keyRequirements = listOf(
        "Marriage registered before the Marriage Registrar under SMA 1954",
        "Separation of 1+ year for Section 28 (Mutual Consent)",
        "Standard fault proof for Section 27 (Cruelty, Desertion, Imprisonment)"
      ),
      requiredEvidence = listOf(
        "Certificate of Marriage issued by Marriage Officer / Registrar",
        "Mutual agreement or petition citing statutory subsection",
        "Proof of living separately"
      ),
      statutoryStatus = "Uniform Civil Ground for all Communities",
      isIllegalWarning = false,
      practicalTips = "Parties who married across religions or chose non-religious registration must file strictly under this Act rather than personal religious laws."
    ),

    DivorceGround(
      id = "christian_parsi_laws",
      title = "Christian & Parsi Divorce Grounds",
      category = GroundCategory.OTHER_LAWS,
      actAndSection = "Indian Divorce Act, 1869 (Sec 10 & 10A) & Parsi Marriage & Divorce Act, 1936",
      shortDescription = "Specific legal grounds governing marriages solemnized under Christian and Zoroastrian/Parsi rites.",
      detailedExplanation = "Section 10A of the Indian Divorce Act provides mutual consent divorce for Christians with 2 years (reduced to 1 year in several High Court rulings) separation. Parsi law under Section 32 provides 10 grounds including non-consummation within 1 year, unsoundness of mind, cruelty, desertion for 2 years, and mutual consent.",
      keyRequirements = listOf(
        "Marriage solemnized under Church rites or Parsi Ashirvad ceremony",
        "Mutual consent requires proof of living separately",
        "Parsi matrimonial disputes are heard by Parsi District Matrimonial Courts assisted by delegates"
      ),
      requiredEvidence = listOf(
        "Church Marriage Certificate or Parsi Matrimonial Register extract",
        "Proof of separation and grounds",
        "Delegates registration for Parsi special court proceedings"
      ),
      statutoryStatus = "Community Specific Personal Law",
      isIllegalWarning = false,
      practicalTips = "Christian spouses can invoke Section 10A mutual consent in District Court for dignified dissolution."
    )
  )

  val lawyers: List<Lawyer> = listOf(
    Lawyer(
      id = "lawyer_1",
      name = "Adv. Shabana Anjum",
      designation = "Senior Matrimonial & Muslim Law Counsel",
      courtEnrolment = "Bar Council of Delhi (D/2140/2007)",
      city = "New Delhi",
      courts = listOf("Delhi High Court", "Saket Family Court", "Tis Hazari"),
      experienceYears = 18,
      languages = listOf("English", "Hindi", "Urdu"),
      specializations = listOf("Triple Talaq Act 2019", "Khula & Muslim Law", "Mutual Consent", "Women Rights & Maintenance"),
      rating = 4.9,
      reviewsCount = 142,
      consultationFee = "₹1,500 / Session",
      phone = "+91 98112 34567",
      email = "adv.shabana.anjum@delhibar.org",
      officeAddress = "Chamber 342, High Court of Delhi, Sher Shah Road, New Delhi",
      aboutBio = "Specializing in matrimonial defense, Khula proceedings, protection under Muslim Women Act 2019, and fast-track mutual consent dissolutions. Trained family court mediator with over 600 settled matrimonial matters.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_2",
      name = "Adv. Rajeshwar Sharma",
      designation = "Principal Counsel - Matrimonial & Child Custody",
      courtEnrolment = "Bar Council of Maharashtra & Goa (MAH/1892/2004)",
      city = "Mumbai",
      courts = listOf("Bombay High Court", "Bandra Family Court", "Thane Family Court"),
      experienceYears = 21,
      languages = listOf("English", "Hindi", "Marathi"),
      specializations = listOf("Mutual Consent Fast-Track", "Child Custody & Guardianship", "High-Net-Worth Alimony", "Contested Divorce"),
      rating = 4.9,
      reviewsCount = 198,
      consultationFee = "₹2,500 / Session",
      phone = "+91 98201 87654",
      email = "rajeshwar.sharma@mumbaicounsel.com",
      officeAddress = "402, Commerce Chambers, Near Fort, Mumbai - 400001",
      aboutBio = "Advocate Sharma has represented clients in complex contested divorces, international child custody disputes, and comprehensive financial asset settlements. Certified cross-border mediator.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_3",
      name = "Adv. Priya Venkatesh",
      designation = "Family Court & Mediation Specialist",
      courtEnrolment = "Karnataka State Bar Council (KAR/3105/2012)",
      city = "Bengaluru",
      courts = listOf("Karnataka High Court", "Bangalore Family Court (Nyaya Degula)"),
      experienceYears = 13,
      languages = listOf("English", "Kannada", "Hindi", "Tamil"),
      specializations = listOf("Mutual Consent (Cooling Waiver)", "Mediation & Settlement", "Special Marriage Act", "Domestic Violence Act"),
      rating = 4.8,
      reviewsCount = 115,
      consultationFee = "₹1,800 / Session",
      phone = "+91 98450 12398",
      email = "priya.venkatesh@lawblr.in",
      officeAddress = "Suite 2B, Lex Chambers, Gandhi Nagar, Bengaluru - 560009",
      aboutBio = "Expertise in fast-tracking mutual consent divorces by securing waiver of the statutory 6-month period under Amardeep Singh guidelines. Strong focus on compassionate mediation and dignity.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_4",
      name = "Adv. Tariq Masood",
      designation = "High Court Advocate & Shariat Jurisprudence Consultant",
      courtEnrolment = "Bar Council of Uttar Pradesh (UP/4590/2009)",
      city = "Lucknow",
      courts = listOf("Allahabad High Court (Lucknow Bench)", "Lucknow Family Court"),
      experienceYears = 16,
      languages = listOf("English", "Hindi", "Urdu"),
      specializations = listOf("Dissolution of Muslim Marriages Act", "Triple Talaq Criminal Defense", "Mubarat & Mahr Recovery", "Family Property"),
      rating = 4.8,
      reviewsCount = 89,
      consultationFee = "₹1,200 / Session",
      phone = "+91 94150 99881",
      email = "tariq.masood@lucknowlaw.org",
      officeAddress = "Hazratganj Legal Enclave, Lucknow - 226001",
      aboutBio = "Leading advocate in Uttar Pradesh regarding Muslim personal law, DMMA 1939 remedies, safeguarding women's Mahr rights and subsistence allowances in compliance with Supreme Court standards.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_5",
      name = "Adv. Ananya Sengupta",
      designation = "Senior Matrimonial Advocate & Human Rights Counsel",
      courtEnrolment = "Bar Council of West Bengal (WB/1204/2006)",
      city = "Kolkata",
      courts = listOf("Calcutta High Court", "Alipore Family Court", "City Civil Court"),
      experienceYears = 19,
      languages = listOf("English", "Bengali", "Hindi"),
      specializations = listOf("Contested Cruelty & Desertion", "Stridhan & Dowry Harassment", "Maintenance u/s 125 CrPC", "Special Marriage Act"),
      rating = 4.9,
      reviewsCount = 164,
      consultationFee = "₹1,500 / Session",
      phone = "+91 98300 44556",
      email = "ananya.sengupta@kolkatalegal.in",
      officeAddress = "Temple Chambers, Old Post Office Street, Kolkata - 700001",
      aboutBio = "Champion of matrimonial dignity and comprehensive settlement. Expert in obtaining urgent ex-parte protection orders and attachment of assets for non-payment of interim maintenance.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_6",
      name = "Adv. Harpreet Singh Gill",
      designation = "NRI Divorce & Cross-Border Matrimonial Counsel",
      courtEnrolment = "Bar Council of Punjab & Haryana (PH/2891/2010)",
      city = "Chandigarh",
      courts = listOf("Punjab & Haryana High Court", "Chandigarh Family Court"),
      experienceYears = 15,
      languages = listOf("English", "Punjabi", "Hindi"),
      specializations = listOf("NRI Marital Abandonment", "Mutual Consent via Power of Attorney", "Anticipatory Bail in 498A", "Lookout Circulars (LOC)"),
      rating = 4.8,
      reviewsCount = 132,
      consultationFee = "₹2,000 / Session",
      phone = "+91 98140 77665",
      email = "hsingh.gill@chandigarhcounsel.com",
      officeAddress = "Sector 17C, Commercial Complex, Chandigarh - 160017",
      aboutBio = "Specializing in NRI matrimonial cases, representation through Special Power of Attorney without requiring travel, video-conference testimony, and swift mutual agreements.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_7",
      name = "Adv. K. Sudhakar Rao",
      designation = "Matrimonial Counsel & Arbitrator",
      courtEnrolment = "Bar Council of Telangana (TS/5120/2005)",
      city = "Hyderabad",
      courts = listOf("Telangana High Court", "City Civil Family Courts Hyderabad"),
      experienceYears = 20,
      languages = listOf("English", "Telugu", "Hindi", "Urdu"),
      specializations = listOf("Hindu Marriage Act", "Muslim Law & Khula", "Asset & Property Partition", "Child Visitation Protocols"),
      rating = 4.9,
      reviewsCount = 175,
      consultationFee = "₹1,800 / Session",
      phone = "+91 98490 66321",
      email = "ksudhakar.rao@hyderabadbar.org",
      officeAddress = "Beside High Court of Telangana, Ghansi Bazaar, Hyderabad - 500002",
      aboutBio = "Dual expertise across civil, Hindu, and Muslim personal laws. Known for calm negotiation and protecting parental relationships during difficult custody disputes.",
      isVerified = true
    ),
    Lawyer(
      id = "lawyer_8",
      name = "Adv. Meenakshi Sundaram",
      designation = "Senior Family Law Practitioner & Counselor",
      courtEnrolment = "Bar Council of Tamil Nadu (TN/2230/2008)",
      city = "Chennai",
      courts = listOf("Madras High Court", "Chennai Principal Family Court"),
      experienceYears = 17,
      languages = listOf("English", "Tamil", "Hindi"),
      specializations = listOf("Mutual Consent", "Christian Divorce Act 1869", "Permanent Alimony Trusts", "Mediation"),
      rating = 4.8,
      reviewsCount = 104,
      consultationFee = "₹1,500 / Session",
      phone = "+91 98400 88219",
      email = "meenakshi.sundaram@chennaicounsel.in",
      officeAddress = "YWCA Building Grounds, Poonamallee High Road, Chennai - 600084",
      aboutBio = "Distinguished advocate focusing on child psychological well-being during divorce, equitable distribution of shared properties, and expeditious Christian & civil divorces.",
      isVerified = true
    )
  )

  val documentChecklist: List<ChecklistItem> = listOf(
    ChecklistItem(
      id = "chk_marriage_cert",
      title = "Marriage Certificate / Registration Proof",
      description = "Issued by Registrar of Marriages or certificate under personal law (e.g., Nikahnama or Church register extract).",
      category = "Identity & Marriage",
      isMandatory = true,
      tips = "If registration certificate is unavailable, marriage invitation card along with wedding photos showing solemnization of rites is accepted."
    ),
    ChecklistItem(
      id = "chk_address_id",
      title = "Government ID & Residence Proofs",
      description = "Aadhaar Card, Passport, or Voter ID showing current addresses of both parties.",
      category = "Identity & Marriage",
      isMandatory = true,
      tips = "Jurisdiction of the Family Court depends on where the wife resides, where marriage was solemnized, or where couple last cohabited."
    ),
    ChecklistItem(
      id = "chk_income_affidavit",
      title = "Income, Asset & Liability Affidavit (Rajnesh v. Neha)",
      description = "Mandatory disclosure mandated by Supreme Court of India detailing salaries, bank accounts, properties, and loans.",
      category = "Financial & Assets",
      isMandatory = true,
      tips = "Must include ITRs for last 3 financial years, recent salary slips for 6 months, and bank statements for all accounts."
    ),
    ChecklistItem(
      id = "chk_bank_statements",
      title = "3-Year Bank Statements & ITRs",
      description = "Comprehensive financial history to assess maintenance, child support capacity, and shared investments.",
      category = "Financial & Assets",
      isMandatory = true,
      tips = "Avoid sudden large transfers prior to filing as courts scrutinize suspicious depletion of funds."
    ),
    ChecklistItem(
      id = "chk_stridhan_inventory",
      title = "Stridhan & Jewelry List (with receipts/photos)",
      description = "Inventory of all gold, jewelry, electronic items, and gifts given to the wife before, during, or after marriage.",
      category = "Financial & Assets",
      isMandatory = false,
      tips = "Stridhan is absolute property of the woman; retention by husband or in-laws constitutes criminal breach of trust."
    ),
    ChecklistItem(
      id = "chk_evidence_grounds",
      title = "Evidence Supporting Ground (For Contested Cases)",
      description = "Police complaints, medical injury reports, abusive chats/emails, or proof of 2+ years desertion.",
      category = "Evidence & Pleadings",
      isMandatory = false,
      tips = "Electronic records (WhatsApp, emails) must be accompanied by Certificate under Section 65B of Indian Evidence Act / Section 63 BSA."
    ),
    ChecklistItem(
      id = "chk_mou_terms",
      title = "Joint Settlement MoU (For Mutual Consent)",
      description = "Detailed agreement specifying total alimony, payment schedule via Demand Draft, child custody and visitation schedule.",
      category = "Evidence & Pleadings",
      isMandatory = false,
      tips = "Ensures no lingering criminal complaints or future disputes remain unresolved."
    )
  )

  val procedureSteps: List<ProcedureStep> = listOf(
    ProcedureStep(
      stepNumber = 1,
      title = "Case Evaluation & Jurisdiction Determination",
      timeEstimate = "1 - 2 Weeks",
      description = "Consult advocate, determine correct personal law and appropriate Family Court jurisdiction (where marriage happened, wife currently resides, or last lived together).",
      keyActions = listOf(
        "Decide between Mutual Consent (Section 13B/28) vs Contested Petition",
        "Collate mandatory documents, ITRs, and marriage registration proof",
        "Issue legal notice if attempting settlement or recording desertion"
      ),
      statutoryNote = "Filing in wrong territorial jurisdiction leads to petition return under Order 7 Rule 10 CPC."
    ),
    ProcedureStep(
      stepNumber = 2,
      title = "Drafting & Presentation of Petition",
      timeEstimate = "1 Week",
      description = "Petition is drafted with verified affidavits, court fees affixed, and filed before Family Court Registry.",
      keyActions = listOf(
        "Detailed statement of facts, dates of marriage and separation",
        "Filing of Section 65B certificate for digital evidence",
        "Listing before the Principal Family Court Judge for admission"
      ),
      statutoryNote = "Both parties must be present in court or represented through legal counsel."
    ),
    ProcedureStep(
      stepNumber = 3,
      title = "First Motion & Notice to Respondent",
      timeEstimate = "2 - 4 Weeks",
      description = "In Mutual Consent, First Motion statements of both spouses are recorded under oath. In contested divorce, summons/notice is issued to the opposite spouse.",
      keyActions = listOf(
        "Recording statement of parties under oath in judge's chamber",
        "Verification that consent is genuine and without force or fraud",
        "Payment of first installment of agreed settlement amount (if mutual)"
      ),
      statutoryNote = "In contested matters, respondent has 30 days to file a Written Statement."
    ),
    ProcedureStep(
      stepNumber = 4,
      title = "Mandatory Counseling & Mediation",
      timeEstimate = "1 - 2 Months",
      description = "Under Section 9 of the Family Courts Act 1984, the court compulsorily refers the parties to trained counselors and mediation center to explore reconciliation or peaceful settlement.",
      keyActions = listOf(
        "Confidential mediation sessions with trained advocate-mediators",
        "Exploring amicable settlement of child custody, alimony, and withdrawal of criminal cross-cases",
        "Preparation of Mediation Agreement or failure report submitted to court"
      ),
      statutoryNote = "Anything spoken or offered during mediation cannot be used as evidence in court trial."
    ),
    ProcedureStep(
      stepNumber = 5,
      title = "Cooling-Off Period (6 Months) & Waiver",
      timeEstimate = "Immediate (if waived) or 6 Months",
      description = "Section 13B(2) mandates a 6-month reflection period between 1st and 2nd motion. However, parties can apply for a waiver application.",
      keyActions = listOf(
        "Filing application for waiver under Amardeep Singh v. Harveen Kaur (SC 2017)",
        "Demonstrating separation has exceeded 18+ months and all mediation concluded",
        "Advancing date for Second Motion"
      ),
      statutoryNote = "Waiver is discretionary upon showing genuine impossibility of reconciliation."
    ),
    ProcedureStep(
      stepNumber = 6,
      title = "Second Motion & Final Decree of Dissolution",
      timeEstimate = "1 - 2 Weeks",
      description = "Parties confirm their final resolution, balance alimony is tendered via Demand Draft, and the judge signs the Decree of Divorce.",
      keyActions = listOf(
        "Final joint statements recorded before the court",
        "Payment of final settlement installment and handover of Stridhan items",
        "Issuance of certified copy of Decree of Dissolution of Marriage"
      ),
      statutoryNote = "Decree is final. Spouses are legally free to remarry after statutory appeal period expires."
    )
  )

  val legalFaqs: List<LegalFaq> = listOf(
    LegalFaq(
      id = "faq_1",
      question = "Is Instant Triple Talaq (Talaq-e-Biddat) valid in India?",
      answer = "NO. Instant Triple Talaq was struck down by the Supreme Court of India in Shayara Bano (2017) and made a cognizable, non-bailable criminal offense under The Muslim Women (Protection of Rights on Marriage) Act, 2019. It has zero legal effect; the marriage remains valid, and the husband can face up to 3 years imprisonment. The wife retains rights to subsistence allowance and custody.",
      tag = "Muslim Law"
    ),
    LegalFaq(
      id = "faq_2",
      question = "How is Alimony / Maintenance calculated in Indian Courts?",
      answer = "There is no fixed statutory percentage, but courts follow the Supreme Court guidelines in Rajnesh v. Neha (2020). Factors include: standard of living during marriage, husband's net disposable income, wife's earning capacity/qualifications, dependent children's educational and medical costs, and age/health of both spouses. Typically, courts award between 20% to 30% of husband's net monthly income as maintenance.",
      tag = "Alimony & Money"
    ),
    LegalFaq(
      id = "faq_3",
      question = "Who gets custody of children during divorce?",
      answer = "Under Indian law (Hindu Minority and Guardianship Act & Guardians and Wards Act), the sole paramount consideration is the 'welfare of the minor child'. Children below 5 years are generally placed under maternal custody unless there is proof of incapacity. For older children, court interviews them in chambers to gauge their preference, granting liberal visitation / joint parenting to the non-custodial parent.",
      tag = "Child Custody"
    ),
    LegalFaq(
      id = "faq_4",
      question = "What belongs to the wife as 'Stridhan'?",
      answer = "Stridhan encompasses all gifts, jewelry, cash, properties, and articles given to the woman before, at the time of, or after marriage by her parents, in-laws, husband, or relatives. It is her exclusive, absolute property. Withholding Stridhan amounts to criminal breach of trust under Section 406 IPC / BNS.",
      tag = "Women's Rights"
    ),
    LegalFaq(
      id = "faq_5",
      question = "Can the 6-month waiting period in mutual divorce be skipped?",
      answer = "YES. In Amardeep Singh v. Harveen Kaur (2017), the Supreme Court held that the 6-month cooling-off period under Section 13B(2) is directory, not mandatory. The Family Court can waive it if the parties have lived apart for over 18 months, mediation has exhausted, and all settlement terms are fully executed.",
      tag = "Mutual Divorce"
    )
  )
}
