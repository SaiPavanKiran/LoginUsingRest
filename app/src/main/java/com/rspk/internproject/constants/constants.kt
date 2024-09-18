package com.rspk.internproject.constants

val stateList: List<String> = listOf(
    "Andhra Pradesh",
    "Arunachal Pradesh",
    "Assam",
    "Bihar",
    "Chandigarh",
    "Chhattisgarh",
    "Dadra and Nagar Haveli and Daman and Diu",
    "Goa",
    "Gujarat",
    "Haryana",
    "Himachal Pradesh",
    "Jammu and Kashmir",
    "Jharkhand",
    "Karnataka",
    "Kerala",
    "Ladakh",
    "Lakshadweep",
    "Madhya Pradesh",
    "Maharashtra",
    "Manipur",
    "Meghalaya",
    "Mizoram",
    "Nagaland",
    "Odisha",
    "Puducherry",
    "Punjab",
    "Rajasthan",
    "Sikkim",
    "Tamil Nadu",
    "Telangana",
    "Tripura",
    "Uttar Pradesh",
    "Uttarakhand",
    "West Bengal"
)


val days = listOf(
    "M",
    "T",
    "W",
    "Th",
    "F",
    "S",
    "Su"
)

val timings = listOf(
    "8:00am - 10:00am",
    "10:00am - 1:00pm",
    "1:00pm - 4:00pm",
    "4:00pm - 7:00pm",
    "7:00pm - 10:00pm"
)

val stateZipCodeRanges = mapOf(
    "Andhra Pradesh" to 500001..539999,
    "Arunachal Pradesh" to 791001..791119,
    "Assam" to 780001..788208,
    "Bihar" to 800001..811114,
    "Chandigarh" to 160001..160071,
    "Chhattisgarh" to 492001..494999,
    "Dadra and Nagar Haveli and Daman and Diu" to 396001..396450, // Includes both Daman and Diu ranges
    "Goa" to 403001..403711,
    "Gujarat" to 380001..396635,
    "Haryana" to 121001..129606,
    "Himachal Pradesh" to 171001..177601,
    "Jammu and Kashmir" to 180001..194599,
    "Jharkhand" to 814001..846006,
    "Karnataka" to 560001..577999,
    "Kerala" to 682001..695039,
    "Ladakh" to 194101..194299,
    "Lakshadweep" to 682555..682559,
    "Madhya Pradesh" to 400001..999999, // Broad range for multiple districts
    "Maharashtra" to 400001..999999, // Broad range for multiple districts
    "Manipur" to 795001..795114,
    "Meghalaya" to 793001..793114,
    "Mizoram" to 796001..796017,
    "Nagaland" to 797001..797115,
    "Odisha" to 751001..769999,
    "Puducherry" to 605001..605013,
    "Punjab" to 140001..154999,
    "Rajasthan" to 300001..342999,
    "Sikkim" to 737001..737136,
    "Tamil Nadu" to 600001..641659,
    "Telangana" to 500001..508999,
    "Tripura" to 799001..799289,
    "Uttar Pradesh" to 200001..284999,
    "Uttarakhand" to 248001..249999,
    "West Bengal" to 700001..742299
)


val countryPhoneRules = mapOf(
    "+1" to 10,   // USA, Canada, etc. - North American Numbering Plan (NANP)
    "+7" to 10,   // Russia and Kazakhstan
    "+20" to 10,  // Egypt
    "+27" to 9,   // South Africa
    "+30" to 10,  // Greece
    "+31" to 9,   // Netherlands
    "+32" to 9,   // Belgium
    "+33" to 9,   // France
    "+34" to 9,   // Spain
    "+36" to 9,   // Hungary
    "+39" to 10,  // Italy (includes Vatican City)
    "+40" to 9,   // Romania
    "+41" to 9,   // Switzerland
    "+43" to 10,  // Austria
    "+44" to 10,  // United Kingdom
    "+45" to 8,   // Denmark
    "+46" to 9,   // Sweden
    "+47" to 8,   // Norway
    "+48" to 9,   // Poland
    "+49" to 10,  // Germany
    "+51" to 9,   // Peru
    "+52" to 10,  // Mexico
    "+53" to 8,   // Cuba
    "+54" to 10,  // Argentina
    "+55" to 10,  // Brazil
    "+56" to 9,   // Chile
    "+57" to 10,  // Colombia
    "+58" to 10,  // Venezuela
    "+60" to 9,   // Malaysia
    "+61" to 9,   // Australia
    "+62" to 11,  // Indonesia
    "+63" to 10,  // Philippines
    "+64" to 9,   // New Zealand
    "+65" to 8,   // Singapore
    "+66" to 9,   // Thailand
    "+81" to 10,  // Japan
    "+82" to 10,  // South Korea
    "+84" to 9,   // Vietnam
    "+86" to 11,  // China
    "+90" to 10,  // Turkey
    "+91" to 10,  // India
    "+92" to 10,  // Pakistan
    "+93" to 9,   // Afghanistan
    "+94" to 9,   // Sri Lanka
    "+95" to 9,   // Myanmar
    "+98" to 10,  // Iran
    "+212" to 9,  // Morocco
    "+213" to 9,  // Algeria
    "+216" to 8,  // Tunisia
    "+218" to 9,  // Libya
    "+220" to 7,  // Gambia
    "+221" to 9,  // Senegal
    "+222" to 8,  // Mauritania
    "+223" to 8,  // Mali
    "+224" to 9,  // Guinea
    "+225" to 8,  // Ivory Coast
    "+226" to 8,  // Burkina Faso
    "+227" to 8,  // Niger
    "+228" to 8,  // Togo
    "+229" to 8,  // Benin
    "+230" to 8,  // Mauritius
    "+231" to 8,  // Liberia
    "+232" to 8,  // Sierra Leone
    "+233" to 9,  // Ghana
    "+234" to 10, // Nigeria
    "+235" to 8,  // Chad
    "+236" to 8,  // Central African Republic
    "+237" to 9,  // Cameroon
    "+238" to 7,  // Cape Verde
    "+240" to 9,  // Equatorial Guinea
    "+241" to 7,  // Gabon
    "+242" to 9,  // Congo
    "+243" to 9,  // Democratic Republic of the Congo
    "+244" to 9,  // Angola
    "+245" to 7,  // Guinea-Bissau
    "+246" to 7,  // British Indian Ocean Territory
    "+248" to 7,  // Seychelles
    "+249" to 9,  // Sudan
    "+250" to 9,  // Rwanda
    "+251" to 9,  // Ethiopia
    "+252" to 9,  // Somalia
    "+253" to 6,  // Djibouti
    "+254" to 9,  // Kenya
    "+255" to 9,  // Tanzania
    "+256" to 9,  // Uganda
    "+257" to 8,  // Burundi
    "+258" to 9,  // Mozambique
    "+260" to 9,  // Zambia
    "+261" to 9,  // Madagascar
    "+262" to 9,  // Reunion
    "+263" to 9,  // Zimbabwe
    "+264" to 9,  // Namibia
    "+265" to 9,  // Malawi
    "+266" to 8,  // Lesotho
    "+267" to 8,  // Botswana
    "+268" to 8,  // Eswatini (Swaziland)
    "+269" to 7,  // Comoros
    "+290" to 4,  // Saint Helena
    "+291" to 7,  // Eritrea
    "+297" to 7,  // Aruba
    "+298" to 6,  // Faroe Islands
    "+299" to 6,  // Greenland
    "+350" to 8,  // Gibraltar
    "+351" to 9,  // Portugal
    "+352" to 9,  // Luxembourg
    "+353" to 9,  // Ireland
    "+354" to 7,  // Iceland
    "+355" to 9,  // Albania
    "+356" to 8,  // Malta
    "+357" to 8,  // Cyprus
    "+358" to 9,  // Finland
    "+359" to 9,  // Bulgaria
    "+370" to 8,  // Lithuania
    "+371" to 8,  // Latvia
    "+372" to 8,  // Estonia
    "+373" to 8,  // Moldova
    "+374" to 8,  // Armenia
    "+375" to 9,  // Belarus
    "+376" to 6,  // Andorra
    "+377" to 8,  // Monaco
    "+378" to 10, // San Marino
    "+380" to 9,  // Ukraine
    "+381" to 9,  // Serbia
    "+382" to 8,  // Montenegro
    "+383" to 9,  // Kosovo
    "+385" to 9,  // Croatia
    "+386" to 8,  // Slovenia
    "+387" to 8,  // Bosnia and Herzegovina
    "+389" to 8,  // North Macedonia
    "+420" to 9,  // Czech Republic
    "+421" to 9,  // Slovakia
    "+423" to 7,  // Liechtenstein
    "+500" to 5,  // Falkland Islands
    "+501" to 7,  // Belize
    "+502" to 8,  // Guatemala
    "+503" to 8,  // El Salvador
    "+504" to 8,  // Honduras
    "+505" to 8,  // Nicaragua
    "+506" to 8,  // Costa Rica
    "+507" to 8,  // Panama
    "+508" to 6,  // Saint Pierre and Miquelon
    "+509" to 8,  // Haiti
    "+590" to 9,  // Guadeloupe
    "+591" to 8,  // Bolivia
    "+592" to 7,  // Guyana
    "+593" to 9,  // Ecuador
    "+594" to 9,  // French Guiana
    "+595" to 9,  // Paraguay
    "+596" to 9,  // Martinique
    "+597" to 7,  // Suriname
    "+598" to 8,  // Uruguay
    "+599" to 7,  // Netherlands Antilles
    "+670" to 7,  // East Timor
    "+672" to 6,  // Australian External Territories
)