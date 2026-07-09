package com.example.model

data class Subject(
    val id: String,
    val name: String,
    val description: String,
    val level: String, // "Form 1", "Form 2", "Form 3", "Form 4", "Form 5", "Form 6"
    val emoji: String,
    val accentType: String, // "green", "blue", "red", "gold", "purple", "orange", "teal"
    val topics: List<Topic> = emptyList()
)

data class Topic(
    val id: String,
    val name: String,
    val content: String, // Detailed notes
    val selfTestQuestions: List<QuizQuestion> = emptyList()
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class PastPaper(
    val id: String,
    val title: String,
    val year: String,
    val subject: String,
    val level: String, // e.g. "Form 4" (CSEE), "Form 6" (ACSEE)
    val questions: List<String>,
    val answers: List<String>
)

object AcademicDatabase {
    val subjects = listOf(
        // ==================== FORM 1 ====================
        Subject(
            id = "f1_physics",
            name = "Physics",
            description = "Mechanics, Heat, Light & Introduction to Physics",
            level = "Form 1",
            emoji = "⚛️",
            accentType = "blue",
            topics = listOf(
                Topic(
                    id = "f1_phys_t1",
                    name = "Introduction to Physics",
                    content = """
                        Physics is the study of matter and its relation to energy. It helps us understand the natural laws governing the universe.
                        
                        ## 1. Branches of Physics:
                        *   **Mechanics**: Deals with motion under the influence of forces.
                        *   **Thermodynamics**: Thermal energy, heat transfer and temperature.
                        *   **Geophysics**: Physics of the earth and earth structures.
                        *   **Astrophysics**: Study of celestial bodies like stars, galaxies.
                        
                        ## 2. Scientific Method:
                        1. Observation
                        2. Hypothesis Formulation
                        3. Experimentation
                        4. Deduction & Conclusion
                        
                        ## 3. Importance of Physics:
                        Physics is fundamental to modern engineering, medicine (MRI, X-Rays), information technology, and construction.
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_phys1",
                            question = "What is the primary definition of Physics?",
                            options = listOf(
                                "The study of living organisms and cells",
                                "The study of chemical changes and bonding",
                                "The study of matter and its relation to energy",
                                "The study of earth soil formations"
                            ),
                            correctAnswerIndex = 2,
                            explanation = "Physics focuses fundamentally on matter, motion, energy, and their universal interactions."
                        )
                    )
                ),
                Topic(
                    id = "f1_phys_t2",
                    name = "Measurement and Density",
                    content = """
                        Measurement is the act of comparison of an unknown physical quantity with a known standard quantity.
                        
                        ## Density (Uzito wa Havu)
                        Density is mass per unit volume of a substance.
                        Formula: Density = Mass (m) / Volume (V)
                        SI Unit: kg/m³ or g/cm³
                        
                        ## Relative Density (R.D)
                        Relative density is the ratio of the density of a substance to the density of pure water.
                        Formula: R.D. = Density of substance / Density of water (1000 kg/m³)
                        *R.D. has NO units because it is a ratio.*
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_phys2",
                            question = "Why does relative density have no SI unit?",
                            options = inlineListOf("Because it is measured in scale", "Because it is a ratio of two identical quantities", "Because it is too small", "Because it represents water"),
                            correctAnswerIndex = 1,
                            explanation = "A ratio of two identical units cancels them out completely, making relative density a pure dimensionless number."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "f1_chemistry",
            name = "Chemistry",
            description = "Matter, Laboratory Practice & Basic Atoms",
            level = "Form 1",
            emoji = "🧪",
            accentType = "red",
            topics = listOf(
                Topic(
                    id = "f1_chem_t1",
                    name = "Introduction to Chemistry",
                    content = """
                        Chemistry is the branch of science that deals with the composition, structure, and properties of matter, and the changes it undergoes.
                        
                        ## 1. Laboratory Safety Equipment:
                        *   **Fume Chamber**: Used to handle toxic/irritant gases safely.
                        *   **Fire Extinguisher**: Prevents laboratory fires.
                        *   **Eye Wash Station**: Rinses accidental chemical spills off eyes.
                        
                        ## 2. Chemical Warning Symbols:
                        *   **Toxic**: Skull and crossbones (e.g., Chlorine, Mercury).
                        *   **Flammable**: Fire flame icon (e.g., Ethanol, Petrol).
                        *   **Corrosive**: Pouring liquid on hand (e.g., Concentrated H2SO4).
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_chem1",
                            question = "Which laboratory equipment is used to handle highly toxic gases?",
                            options = inlineListOf("Pipette support", "Fume Chamber", "Analytical Balance", "Burette stand"),
                            correctAnswerIndex = 1,
                            explanation = "Fume chambers prevent hazardous gases from entering the main lab airspace."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "f1_biology",
            name = "Biology",
            description = "Introduction to Life Science & Cell Biology",
            level = "Form 1",
            emoji = "🌿",
            accentType = "green",
            topics = listOf(
                Topic(
                    id = "f1_bio_t1",
                    name = "Introduction to Biology",
                    content = """
                        Biology comes from two Greek words: 'Bios' (meaning Life) and 'Logos' (meaning Study).
                        Therefore, Biology is the study of living things (organisms).
                        
                        ## Characteristics of Living Organisms (MRS GREN):
                        1.  **Movement**: Ability to shift position.
                        2.  **Respiration**: Releasing energy from food.
                        3.  **Sensitivity**: Detecting changes in the environment.
                        4.  **Growth**: Permanent increase in size/mass.
                        5.  **Reproduction**: Production of offspring.
                        6.  **Excretion**: Removal of metabolic toxic wastes.
                        7.  **Nutrition**: Intake of nutrients for energy.
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_bio1",
                            question = "What does the letter 'E' represent in the MRS GREN life characteristics acronym?",
                            options = inlineListOf("Environment", "Ecology", "Excretion", "Energy"),
                            correctAnswerIndex = 2,
                            explanation = "Excretion is the critical removal of toxic metabolic waste from living systems."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "f1_math",
            name = "Mathematics",
            description = "Integers, Fractions, Decimals & Geometry",
            level = "Form 1",
            emoji = "➗",
            accentType = "blue",
            topics = listOf(
                Topic(
                    id = "f1_math_t1",
                    name = "Numbers & Integers",
                    content = """
                        ## Integers (Namba Nzima)
                        Integers are whole numbers that can be positive, negative, or zero (e.g., ..., -3, -2, -1, 0, 1, 2, 3, ...).
                        
                        ## Rules for Math Operations:
                        *   **Addition of Negative Numbers**: (-a) + (-b) = -(a+b)
                        *   **Multiplication Rule (Dau la Sifa)**:
                            *   (+) × (+) = (+)
                            *   (-) × (-) = (+)
                            *   (+) × (-) = (-)
                            *   (-) × (+) = (-)
                        
                        ## BODMAS / PEMDAS
                        Always prioritize mathematical expressions in this strict order:
                        1. **B**rackets (Mabano)
                        2. **O**f (Kuzidisha kwa 'ya')
                        3. **D**ivision (Kugawanya)
                        4. **M**ultiplication (Kuzidisha)
                        5. **A**ddition (Kujumlisha)
                        6. **S**ubtraction (Kutoa)
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_math1",
                            question = "Evaluate the expression: 12 + 6 ÷ 2 - 3 × 2",
                            options = inlineListOf("3", "9", "12", "6"),
                            correctAnswerIndex = 1,
                            explanation = "BODMAS process: First divide (6 ÷ 2 = 3), then multiply (3 × 2 = 6). Expression becomes: 12 + 3 - 6 = 9."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "f1_history",
            name = "History",
            description = "Evolution of Man & Pre-Colonial African Societies",
            level = "Form 1",
            emoji = "📜",
            accentType = "gold",
            topics = listOf(
                Topic(
                    id = "f1_hist_t1",
                    name = "Sources of Historical Information",
                    content = """
                        History is the study of past human activities and events over time.
                        
                        ## Primary Sources of History:
                        1.  **Oral Traditions**: Word of mouth, stories, folk songs. Easy to distort over generations.
                        2.  **Archaeology**: Excavating fossils, tools, pottery from the ground. Olduvai Gorge is a primary example in Tanzania.
                        3.  **Historical Sites**: Places of cultural heritage (e.g., Kondoa Irangi caves, Kilwa Kisiwani ruins).
                        
                        ## Secondary Sources of History:
                        *   Books, articles, academic journals, museums, and documentaries.
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_hist1",
                            question = "Which archaeological site in Tanzania is famous for the remains of early hominids discovered by Dr. Louis Leakey?",
                            options = inlineListOf("Kalambo Falls", "Olduvai Gorge", "Kilwa ruins", "Kondoa Caves"),
                            correctAnswerIndex = 1,
                            explanation = "Olduvai Gorge is globally renowned for breakthroughs in understanding human evolution."
                        )
                    )
                )
            )
        ),

        // ==================== FORM 2 ====================
        Subject(
            id = "f2_physics",
            name = "Physics",
            description = "Magnetism, Static Electricity & Force and Motion",
            level = "Form 2",
            emoji = "⚛️",
            accentType = "blue",
            topics = listOf(
                Topic(
                    id = "f2_phys_t1",
                    name = "Newton's Laws of Motion",
                    content = """
                        Sir Isaac Newton proposed three laws of motion that govern physical dynamics.
                        
                        ## 1. Newton's First Law (Law of Inertia):
                        Every object continues in its state of rest or uniform motion in a straight line unless compelled to change that state by an external force.
                        *Example: A passenger leaning forward when a bus brakes suddenly.*
                        
                        ## 2. Newton's Second Law:
                        The rate of change of momentum is directly proportional to the applied force and takes place in the direction of the force.
                        Formula: Force (F) = mass (m) × acceleration (a)
                        
                        ## 3. Newton's Third Law:
                        For every action, there is an equal and opposite reaction.
                        *Example: Recoil of a fired gun or jet propulsion.*
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_phys3",
                            question = "Which law explains why a jet rocket launches upwards as hot combustion exhaust exits downwards?",
                            options = inlineListOf("First Law", "Second Law", "Third Law (Action-Reaction)", "Law of Universal Gravitation"),
                            correctAnswerIndex = 2,
                            explanation = "Jet propulsion relies on Newton's 3rd Law: action exhaust downwards causes an equal and opposite reaction force upwards."
                        )
                    )
                )
            )
        ),
        Subject(
            id = "f2_chemistry",
            name = "Chemistry",
            description = "Oxygen, Hydrogen & Water Formations",
            level = "Form 2",
            emoji = "🧪",
            accentType = "red",
            topics = listOf(
                Topic(
                    id = "f2_chem_t1",
                    name = "Oxygen and Combustion",
                    content = """
                        Oxygen is an extremely active element making up about 21% of atmospheric air by volume.
                        
                        ## Laboratory Preparation of Oxygen
                        Oxygen is prepared by decomposition of Hydrogen Peroxide (H2O2) using Manganese (IV) Oxide (MnO2) as a catalyst:
                        Reaction: 2H2O2 (aq) ──► 2H2O (l) + O2 (g)
                        
                        ## Chemical Properties of Oxygen:
                        *   Supports combustion (fuel burns in it).
                        *   Causes rusting of iron in the presence of moisture.
                        *   Forming basic oxides with metals (e.g., MgO).
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_chem2",
                            question = "What is the catalyst used in the laboratory preparation of Oxygen from Hydrogen Peroxide?",
                            options = inlineListOf("Sodium Chloride", "Manganese (IV) Oxide", "Iron filings", "Copper Sulfate"),
                            correctAnswerIndex = 1,
                            explanation = "Manganese (IV) Oxide acts as a highly efficient catalyst to accelerate H2O2 decomposition."
                        )
                    )
                )
            )
        ),

        // ==================== FORM 3 ====================
        Subject(
            id = "f3_biology",
            name = "Biology",
            description = "Ecology, Genetics & Human Health Diseases",
            level = "Form 3",
            emoji = "🌿",
            accentType = "green",
            topics = listOf(
                Topic(
                    id = "f3_bio_t1",
                    name = "Ecology & Ecosystems",
                    content = """
                        Ecology is the study of the interactions between living organisms and their physical environment.
                        
                        ## Component of an Ecosystem:
                        1.  **Biotic Factors**: Living components (Producers, Consumers, Decomposers).
                        2.  **Abiotic Factors**: Non-living factors (Temperature, Soil pH, Rainfall, Solar illumination).
                        
                        ## Food Chains and Webs:
                        A food chain shows the linear flow of energy:
                        Grass (Producer) ──► Zebra (Primary Consumer) ──► Lion (Secondary Consumer).
                        A food web shows interconnected complex energetic dependency webs in the habitat.
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_bio3",
                            question = "In an ecosystem, green plants are referred to as:",
                            options = inlineListOf("Primary consumers", "Decomposers", "Producers", "Herbivores"),
                            correctAnswerIndex = 2,
                            explanation = "Green plants produce their own food through photosynthesis, utilizing solar energy."
                        )
                    )
                )
            )
        ),

        // ==================== FORM 4 ====================
        Subject(
            id = "f4_physics",
            name = "Physics",
            description = "CSEE Secondary Revision & National Examination Studies",
            level = "Form 4",
            emoji = "⚛️",
            accentType = "blue",
            topics = listOf(
                Topic(
                    id = "f4_phys_t1",
                    name = "Radioactivity and Nuclear Physics",
                    content = """
                        Radioactivity is the spontaneous disintegration of unstable atomic nuclei, leading to emission of ionizing radiations.
                        
                        ## Types of Radioactive Emissions:
                        1.  **Alpha Particles (α)**: Helium nuclei. Heaviest, highly ionizing, low penetrating power (stopped by sheet of paper).
                        2.  **Beta Particles (β)**: High-speed electrons. Moderate penetration and ionization.
                        3.  **Gamma Rays (γ)**: Extremely high-frequency electromagnetic waves. Uncharged, very low ionization, maximum penetration power (requires thick lead shields).
                        
                        ## Nuclear Fusion vs Fission:
                        *   **Nuclear Fission**: Heavy nucleus splitting into lighter nuclei (e.g., Atomic reactor core).
                        *   **Nuclear Fusion**: Lighter nuclei combining into a heavier stable nucleus (e.g., core reactions of the Sun).
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_phys4",
                            question = "Which type of radioactive radiation possesses the highest penetrating power?",
                            options = inlineListOf("Alpha particles", "Beta particles", "Gamma rays", "Positrons"),
                            correctAnswerIndex = 2,
                            explanation = "Gamma rays are uncharged electromagnetic waves, enabling them to bypass materials easily unless blocked by high-density lead."
                        )
                    )
                )
            )
        ),

        // ==================== FORM 5 ====================
        Subject(
            id = "f5_economics",
            name = "Economics",
            description = "Microeconomics, Price Theory & Advanced Production",
            level = "Form 5",
            emoji = "💰",
            accentType = "orange",
            topics = listOf(
                Topic(
                    id = "f5_econ_t1",
                    name = "Theory of Demand and Supply",
                    content = """
                        Demand is the willingness and financial ability of consumers to purchase a given commodity at alternative price levels in a specific timeframe.
                        
                        ## 1. The Law of Demand:
                        Ceteris Paribus (other things remaining constant), the higher the price of a good, the lower the quantity demanded, and vice versa.
                        *The demand curve slopes downwards from left to right.*
                        
                        ## 2. Exceptions to the Law of Demand:
                        *   **Giffen Goods**: Highly inferior basic essentials.
                        *   **Veblen Goods (Conspicuous Consumption)**: Luxury goods bought for prestige or status.
                        *   **Goods of Speculative Expectation**: Goods whose price is expected to rise further.
                    """.trimIndent(),
                    selfTestQuestions = listOf(
                        QuizQuestion(
                            id = "q_econ1",
                            question = "What economic anomaly is characterized by Giffen or Veblen goods regarding the Law of Demand?",
                            options = inlineListOf("They conform strictly to the law", "They have horizontal demand curves", "They violate the Law of Demand", "They have negative cost indexes"),
                            correctAnswerIndex = 2,
                            explanation = "For luxury (Veblen) status items or extreme inferior basic staples (Giffen), the quantity demanded can actually increase as price rises."
                        )
                    )
                )
            )
        )
    )

    val pastPapers = listOf(
        PastPaper(
            id = "paper_phys_2024",
            title = "Physics 1 - National Examination (CSEE)",
            year = "2024",
            subject = "Physics",
            level = "Form 4",
            questions = listOf(
                "1. State Newton's Second Law of Motion and derive the relationship F = ma.",
                "2. Define Relative Density and explain how to determine the density of an irregular solid using a Eureka can.",
                "3. A radio transmitter operates at 100 MHz. Calculate the wavelength of its emissions (Speed of light c = 3 x 10^8 m/s)."
            ),
            answers = listOf(
                "1. Newton's 2nd law states that rate of change of momentum is proportional to net applied force. Momentum p = mv. F is proportional to d(mv)/dt. For constant mass m, F = m(dv/dt) = ma.",
                "2. R.D is the density of a substance compared to water. Lower the irregular solid in water, measure displaced water volume (which matches solid volume), obtain mass, then compute mass/volume.",
                "3. Use lambda = c / f. Frequency f = 100 x 10^6 Hz. lambda = 3 x 10^8 / 10^8 = 3.0 meters."
            )
        ),
        PastPaper(
            id = "paper_chem_2023",
            title = "Chemistry Theory - National Examination (CSEE)",
            year = "2023",
            subject = "Chemistry",
            level = "Form 4",
            questions = listOf(
                "1. Describe laboratory preparation of Oxygen from Hydrogen Peroxide.",
                "2. Balance the chemical equation: Fe + O2 -> Fe2O3.",
                "3. What is the electroplating process of zinc?"
            ),
            answers = listOf(
                "1. Peroxide reacts in the presence of MnO2 catalyst producing liquid water and free oxygen gas collected over water.",
                "2. Balanced state: 4Fe + 3O2 -> 2Fe2O3.",
                "3. Pass direct current through Zn anode and metal cathode containing a zinc solution, depositing thin protective zinc coating."
            )
        )
    )

    private fun inlineListOf(vararg items: String) = items.toList()
}
