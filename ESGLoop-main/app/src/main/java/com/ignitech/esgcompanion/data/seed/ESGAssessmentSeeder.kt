package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.google.gson.Gson

object ESGAssessmentSeeder {
    
    private val gson = Gson()
    
    fun getESGAssessmentQuestions(): List<ESGQuestionEntity> {
        return listOf(
            // Environmental Questions
            ESGQuestionEntity(
                id = "env_001",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "1. The financial institution measures and discloses the biodiversity footprint of its portfolio.",
                description = "Measuring and disclosing biodiversity footprint helps assess environmental impact of investment portfolio.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "env_002",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "2. Companies prevent negative impacts on High Conservation Value (HCV) areas within their business operations and the areas they manage.",
                description = "Protecting HCV areas is crucial for biodiversity conservation and sustainable business practices.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "env_003",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "3. Companies prevent negative impacts on protected areas that fall under the categories I-IV of the International Union for Conservation of Nature (IUCN) within their business operations and the areas they manage.",
                description = "IUCN protected areas are critical for global biodiversity conservation.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "env_004",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "4. Companies prevent negative impacts on UNESCO World Heritage sites within their business operations and the areas they manage.",
                description = "UNESCO World Heritage sites have outstanding universal value and must be protected.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "env_005",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "5. Companies prevent negative impacts on protected areas that fall under the Ramsar Convention on Wetlands within their business operations and the areas they manage.",
                description = "Ramsar wetlands are internationally important for waterfowl and biodiversity.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            ESGQuestionEntity(
                id = "env_006",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Biodiversity",
                question = "6. Companies prevent negative impacts on endangered plant and animal species",
                description = "Protecting endangered species is essential for maintaining ecosystem balance.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 6
            ),
            ESGQuestionEntity(
                id = "env_007",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "For its own direct and indirect greenhouse gas emissions, the financial institution establishes measurable reduction objectives that is aligned with limiting the maximum global temperature increase of 1.5°C",
                description = "1.5°C alignment is critical for climate change mitigation.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 7
            ),
            ESGQuestionEntity(
                id = "env_008",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "The financial institution discloses its financed emissions - meaning the absolute scope 1 and scope 2 GHG emissions associated with the companies in its lending and/or investment portfolio(s).",
                description = "Financed emissions disclosure is essential for climate transparency.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 8
            ),
            ESGQuestionEntity(
                id = "env_009",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "The financial institution discloses its financed emissions - meaning the absolute scope 1, 2, and 3 emissions associated with the companies in its lending and/or investment portfolio(s).",
                description = "Scope 3 emissions provide complete picture of climate impact.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 9
            ),
            ESGQuestionEntity(
                id = "env_010",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "For large scale project financing, the financial institution requires environmental impact assessments that include data on greenhouse gas emissions and climate risks.",
                description = "Climate risk assessment is crucial for sustainable project financing.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 10
            ),
            ESGQuestionEntity(
                id = "env_011",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "For its financed and invested greenhouse gas emissions, the financial institution establishes short, medium and long-term absolute reduction objectives that are aligned with limiting the maximum global temperature increase to 1.5°C.",
                description = "Comprehensive emission reduction targets across all timeframes ensure climate alignment.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 11
            ),
            ESGQuestionEntity(
                id = "env_012",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "The financial institution establishes sector-specific reduction targets for its financed emissions that are aligned with limiting the maximum global temperature increase to 1.5°C.",
                description = "Sector-specific targets ensure tailored climate action across different industries.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 12
            ),
            ESGQuestionEntity(
                id = "env_013",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change",
                question = "The financial institution measures and discloses climate-related impacts in line with IFRS S2 Climate-related Disclosures or the recommendations by the Task Force on Climate-related Financial Disclosures.",
                description = "Standardized climate disclosure frameworks ensure consistent and comparable reporting.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 13
            ),
            ESGQuestionEntity(
                id = "env_014",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "Companies involved in the development of new thermal coal mines are excluded from investment and financing.",
                description = "Coal mining exclusion prevents support for high-carbon infrastructure development.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 14
            ),
            ESGQuestionEntity(
                id = "env_015",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "Companies involved in the development of new coal-fired power plants are excluded from investment and financing.",
                description = "Coal power exclusion prevents support for high-emission energy infrastructure.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 15
            ),
            ESGQuestionEntity(
                id = "env_016",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "The financial institution excludes financing and investing in companies active in thermal coal mining for more than 20% of their activities.",
                description = "Coal mining threshold exclusion ensures limited exposure to high-carbon activities.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 16
            ),
            ESGQuestionEntity(
                id = "env_017",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "The financial institution excludes financing and investing in companies active in coal fired power for more than 20% of their activities.",
                description = "Coal power threshold exclusion limits exposure to high-emission energy generation.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 17
            ),
            ESGQuestionEntity(
                id = "env_018",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "The financial institution has a time-bound phase-out strategy for coal that is aligned with a 1.5-degree climate scenario.",
                description = "Coal phase-out strategy ensures systematic transition away from high-carbon energy.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 18
            ),
            ESGQuestionEntity(
                id = "env_019",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "Companies engaged in new oil and gas exploration and development are excluded from investment and financing.",
                description = "Oil and gas exploration exclusion prevents support for new fossil fuel development.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 19
            ),
            ESGQuestionEntity(
                id = "env_020",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Fossil Fuels",
                question = "The financial institution has a time-bound phase-out strategy for oil and gas that is aligned with a 1.5 degree scenario.",
                description = "Oil and gas phase-out strategy ensures systematic transition to renewable energy.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 20
            ),
            
            // Social Questions
            ESGQuestionEntity(
                id = "soc_001",
                pillar = ESGPillar.SOCIAL,
                category = "Financial Inclusion",
                question = "1. The financial institution has policies, services and products that specifically target un-banked people, under-banked people and/or MSMEs",
                description = "Financial inclusion promotes economic development and reduces inequality.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "soc_002",
                pillar = ESGPillar.SOCIAL,
                category = "Financial Inclusion",
                question = "2. The financial institution has branches in rural areas, not only in cities.",
                description = "Rural branches improve access to financial services for underserved communities.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "soc_003",
                pillar = ESGPillar.SOCIAL,
                category = "Financial Inclusion",
                question = "3. The financial institution provides branchless, cashless (e-money) and mobile banking services.",
                description = "Digital banking services increase financial accessibility and convenience.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "soc_004",
                pillar = ESGPillar.SOCIAL,
                category = "Financial Inclusion",
                question = "4. The financial institution's share of loans channelled to MSMEs is above 10%.",
                description = "MSME financing supports entrepreneurship and job creation.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "soc_005",
                pillar = ESGPillar.SOCIAL,
                category = "Financial Inclusion",
                question = "5. The financial institution does not require collateral for MSMEs to borrow.",
                description = "Collateral-free loans improve access to finance for small businesses.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            ESGQuestionEntity(
                id = "soc_006",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "1. Use, production, development, maintenance, testing, stockpiling of and trade in anti-personnel landmines, including key components of landmines, are unacceptable.",
                description = "Anti-personnel landmines cause severe humanitarian harm and violate international law.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "soc_007",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "2. Use, production, development, maintenance, testing, stockpiling of and trade in cluster munitions, including key components of cluster munitions, are unacceptable.",
                description = "Cluster munitions cause indiscriminate harm to civilians and violate humanitarian law.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "soc_008",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "3. Production, development, maintenance, testing, stockpiling of and trade in incendiary weapons, including key components of incendiary weapons, are unacceptable",
                description = "Incendiary weapons cause excessive suffering and violate humanitarian principles.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "soc_009",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "4. Use, production, development, maintenance, testing, stockpiling of and trade in nuclear weapons, including key components of nuclear weapons, are unacceptable.",
                description = "Nuclear weapons pose existential threats to humanity and violate international law.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "soc_010",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "5. Use, production, development, maintenance, testing, stockpiling of and trade in chemical weapons, including key components of chemical weapons, are unacceptable.",
                description = "Chemical weapons cause severe suffering and are prohibited under international law.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            ESGQuestionEntity(
                id = "soc_011",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "6. Use, production, development, maintenance, testing, stockpiling of and trade in biological weapons, including key components of biological weapons, are unacceptable.",
                description = "Biological weapons pose severe risks to public health and are internationally prohibited.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 6
            ),
            ESGQuestionEntity(
                id = "soc_012",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "7. Use, production, development, maintenance, testing, stockpiling of and trade in lethal autonomous weapons systems (LAWS), including key components of LAWS, are unacceptable.",
                description = "LAWS raise serious ethical and humanitarian concerns about autonomous killing.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 7
            ),
            ESGQuestionEntity(
                id = "soc_013",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "8. Companies treat dual-use goods as military goods when they have a non-civilian purpose.",
                description = "Proper classification of dual-use goods prevents misuse for military purposes.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 8
            ),
            ESGQuestionEntity(
                id = "soc_014",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "9. Supply of military goods to countries that are under a United Nations or relevant multilateral arms embargo, is unacceptable.",
                description = "Arms embargo compliance prevents support for sanctioned regimes.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 9
            ),
            ESGQuestionEntity(
                id = "soc_015",
                pillar = ESGPillar.SOCIAL,
                category = "Human Rights",
                question = "10. Supply of military goods is unacceptable if there is an overriding risk that the arms will be used for serious violation of international human rights and humanitarian law.",
                description = "Human rights risk assessment prevents arms sales to abusive regimes.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 10
            ),
            ESGQuestionEntity(
                id = "soc_016",
                pillar = ESGPillar.SOCIAL,
                category = "Labour Rights",
                question = "1. The financial institution respects the ILO Declaration on Fundamental Principles and Rights at Work.",
                description = "ILO principles ensure respect for fundamental labour rights.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "soc_017",
                pillar = ESGPillar.SOCIAL,
                category = "Labour Rights",
                question = "2. The financial institution integrates at least the labour standards of the ILO Declaration on Fundamental Principles and Rights at Work in its procurement policies.",
                description = "Procurement policies ensure labour standards throughout supply chains.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "soc_018",
                pillar = ESGPillar.SOCIAL,
                category = "Labour Rights",
                question = "3. The financial institution respects the ILO Maternity Protection Convention.",
                description = "Maternity protection ensures women's rights in the workplace.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "soc_019",
                pillar = ESGPillar.SOCIAL,
                category = "Labour Rights",
                question = "4. The financial institution establishes procedures for managing and processing employee complaints and solve labour rights violations, preferably in consultation with the relevant trade union.",
                description = "Complaint procedures ensure effective resolution of labour disputes.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "soc_020",
                pillar = ESGPillar.SOCIAL,
                category = "Labour Rights",
                question = "5. Companies uphold the freedom of association and the effective recognition of the right to collective bargaining",
                description = "Freedom of association enables workers to organize and negotiate collectively.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            
            // Governance Questions
            ESGQuestionEntity(
                id = "gov_001",
                pillar = ESGPillar.GOVERNANCE,
                category = "Consumer Protection",
                question = "1. The financial institution has a policy to disclose client's rights and the risks of products and services.",
                description = "Transparent disclosure protects consumer rights and promotes informed decision-making.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "gov_002",
                pillar = ESGPillar.GOVERNANCE,
                category = "Consumer Protection",
                question = "2. The financial institution has a policy that regulates staff ethics in serving clients in a non-discriminatory way.",
                description = "Ethical conduct policies ensure fair treatment of all clients.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "gov_003",
                pillar = ESGPillar.GOVERNANCE,
                category = "Consumer Protection",
                question = "3. The financial institution ensures that consumers have access to adequate complaints handling and redress that have a due diligence process in place.",
                description = "Effective complaint mechanisms protect consumer rights and build trust.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "gov_004",
                pillar = ESGPillar.GOVERNANCE,
                category = "Consumer Protection",
                question = "4. The financial institution discloses the results of complaints monitoring such as number of complaints, main issues, in which institutions/body for consumers defence the complaints where registered, and from which channels they were received.",
                description = "Transparent complaint reporting demonstrates accountability and continuous improvement.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "gov_005",
                pillar = ESGPillar.GOVERNANCE,
                category = "Consumer Protection",
                question = "5. The financial institution has public commitments to reduce consumer complaints, fixing goals to achieve and making this information accessible to any stakeholder.",
                description = "Public commitments to reduce complaints show commitment to customer satisfaction.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            ESGQuestionEntity(
                id = "gov_006",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "1. The financial institution describes its finance and investment framework regarding environmental and social issues and provides insight into how the financial institution ensures that investments meet the conditions set in its policies.",
                description = "Transparent framework disclosure builds stakeholder trust and accountability.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "gov_007",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "2. The financial institution publishes the names of companies in which it invests.",
                description = "Investment transparency enables stakeholders to assess portfolio alignment with ESG principles.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            ESGQuestionEntity(
                id = "gov_008",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "3. The financial institution mentions all companies (on its website) to which it grants new credits.",
                description = "Credit transparency demonstrates commitment to responsible lending practices.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 3
            ),
            ESGQuestionEntity(
                id = "gov_009",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "4. The financial institution mentions all companies (on its website) to which it has granted credits.",
                description = "Historical credit transparency provides complete picture of lending activities.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 4
            ),
            ESGQuestionEntity(
                id = "gov_010",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "5. The financial institution discloses the names of all outstanding project finance transactions and project-related corporate loans, including the information required by the Equator Principles 4.",
                description = "Project finance transparency aligns with international best practices and stakeholder expectations.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 5
            ),
            ESGQuestionEntity(
                id = "gov_011",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "6. The financial institution publishes a breakdown of its portfolio by region, size and industry.",
                description = "Portfolio breakdown provides transparency about investment distribution and focus areas.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 6
            ),
            ESGQuestionEntity(
                id = "gov_012",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "7. The financial institution publishes a sufficiently detailed breakdown of its portfolio, for example based on the first two digits of NACE and ISIC.",
                description = "Detailed portfolio classification enables stakeholders to assess sector exposure and risks.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 7
            ),
            ESGQuestionEntity(
                id = "gov_013",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "8. The financial institution publishes the number of companies with which it has engaged on social and environmental topics.",
                description = "Engagement metrics demonstrate active stewardship and ESG integration efforts.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 8
            ),
            ESGQuestionEntity(
                id = "gov_014",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "9. The financial institution publishes the names of companies with which it has engaged on social and environmental topics.",
                description = "Named engagement disclosure provides transparency about stewardship activities.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 9
            ),
            ESGQuestionEntity(
                id = "gov_015",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "10. The financial institution publishes the results of engagement on social and environmental topics, including the topics, goals and deadlines.",
                description = "Engagement results disclosure demonstrates accountability and impact measurement.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 10
            ),
            ESGQuestionEntity(
                id = "gov_016",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "11. The financial institution publishes the names of companies that are excluded from investment and financing due to sustainability issues, including the reasons for this exclusion.",
                description = "Exclusion disclosure provides transparency about ESG risk management and decision-making.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 11
            ),
            ESGQuestionEntity(
                id = "gov_017",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "12. The financial institution discloses a voting policy which explains how environmental and social issues are integrated into its voting decisions.",
                description = "Voting policy disclosure demonstrates commitment to ESG integration in corporate governance.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 12
            ),
            ESGQuestionEntity(
                id = "gov_018",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "13. The financial institution publishes its voting record.",
                description = "Voting record disclosure provides transparency about proxy voting decisions and ESG considerations.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 13
            ),
            ESGQuestionEntity(
                id = "gov_019",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "14. The financial institution publishes a sustainability report that is set up in accordance with recognised sustainability reporting frameworks.",
                description = "Sustainability reporting ensures consistent and comparable ESG disclosure across the industry.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 14
            ),
            ESGQuestionEntity(
                id = "gov_020",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "15. The financial institution's sustainability report has been verified externally.",
                description = "External verification ensures credibility and reliability of sustainability reporting.",
                optionsJson = gson.toJson(listOf(
                    mapOf("text" to "Fully implemented", "score" to 4),
                    mapOf("text" to "Partially implemented", "score" to 2),
                    mapOf("text" to "Not implemented", "score" to 0),
                    mapOf("text" to "Not applicable", "score" to 0)
                )),
                weight = 1,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 15
            )
        )
    }
}