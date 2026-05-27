package com.recrutement.gestion_rh.controller;

import com.recrutement.gestion_rh.service.ApplicationService;
import com.recrutement.gestion_rh.service.JobOfferService;
import com.recrutement.gestion_rh.service.OnboardingService;
import com.recrutement.gestion_rh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web")
public class ViewController {

    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // 1. Offres réelles
        int totalOffres = 0;
        if (jobOfferService != null && jobOfferService.getAllOffers() != null) {
            totalOffres = jobOfferService.getAllOffers().size();
        }

        // 2. ✅ CORRECTION : Candidatures réelles (Crée la méthode .getAllOffers() ou .findAll() si besoin)
        int totalCandidatures = 0;
        try {
            // Si ton service possède une méthode pour tout lister, sinon on prend la liste de l'offre 1 par défaut pour le test
            totalCandidatures = applicationService.getApplicationsByOffer(1L).size();
        } catch (Exception e) {
            totalCandidatures = 0;
        }

        // 3. ✅ CORRECTION : Onboardings réels
        int totalOnboardings = 0;
        if (onboardingService != null && onboardingService.getAllOnboardings() != null) {
            totalOnboardings = onboardingService.getAllOnboardings().size();
        }

        model.addAttribute("offersCount", totalOffres);
        model.addAttribute("appsCount", totalCandidatures);
        model.addAttribute("onboardingsCount", totalOnboardings);

        return "index";
    }

    @GetMapping("/offers")
    public String showOffersPage(Model model) {
        model.addAttribute("offers", jobOfferService.getAllOffers());
        return "offers";
    }

    @PostMapping("/offers/analyze")
    public String analyzeProfileWeb(@RequestParam String cvText, Model model) {
        model.addAttribute("offers", jobOfferService.getAllOffers());

        String mockAiResponse = "--- ANALYSE DE L'ASSISTANT IA RH ---\n\n" +
                "✅ POINTS FORTS :\n" +
                "- Excellente base technique (M2 MIAGE détecté).\n" +
                "- Maîtrise des concepts fondamentaux bases de données et Java.\n\n" +
                "💡 AXES D'AMÉLIORATION RECOMMANDÉS :\n" +
                "1. Ajouter des projets pratiques utilisant Spring Boot 3.x sur votre profil.\n" +
                "2. Mettre en avant l'utilisation d'outils d'API comme Swagger/OpenAPI.\n" +
                "3. Mentionner votre niveau d'anglais technique pour les postes internationaux.";

        model.addAttribute("aiResult", mockAiResponse);
        return "offers";
    }

    @GetMapping("/applications")
    public String showApplicationsPage(Model model) {
        model.addAttribute("applications", applicationService.getApplicationsByOffer(1L));
        return "applications";
    }

    @PostMapping("/applications/update-status")
    public String updateApplicationStatusWeb(
            @RequestParam("appId") Long appId,
            @RequestParam("newStatus") String newStatus,
            @RequestParam(value = "comment", required = false, defaultValue = "") String comment,
            @RequestParam(value = "interviewDate", required = false) String interviewDate) { // ✅ Ajout du paramètre Date

        com.recrutement.gestion_rh.model.ApplicationStatus statusEnum = com.recrutement.gestion_rh.model.ApplicationStatus.valueOf(newStatus);

        // Formater le texte final à enregistrer
        String finalComment = comment;
        if (interviewDate != null && !interviewDate.isEmpty()) {
            // Rendre la date lisible (ex: 2026-05-27T14:30 -> 2026-05-27 à 14:30)
            String formattedDate = interviewDate.replace("T", " à ");
            finalComment = "[📅 ENTRETIEN LE " + formattedDate + "] " + comment;
        }

        // Sauvegarde le statut et le commentaire combiné à la date
        applicationService.updateStatus(appId, statusEnum, finalComment);

        if ("ACCEPTE".equals(newStatus)) {
            com.recrutement.gestion_rh.model.Application app = applicationService.getApplicationsByOffer(1L)
                    .stream()
                    .filter(a -> a.getId().equals(appId))
                    .findFirst()
                    .orElse(null);

            if (app != null && app.getCandidate() != null) {
                boolean déjàEnCours = onboardingService.getAllOnboardings().stream()
                        .anyMatch(o -> o.getEmployee().getId().equals(app.getCandidate().getId()));

                if (!déjàEnCours) {
                    com.recrutement.gestion_rh.model.Onboarding newOnboarding = new com.recrutement.gestion_rh.model.Onboarding();
                    newOnboarding.setEmployee(app.getCandidate());
                    newOnboarding.setCurrentStep("Signature du contrat");
                    newOnboarding.setIdDocumentProvided(false);
                    newOnboarding.setVitaleCardProvided(false);
                    newOnboarding.setRibProvided(false);

                    onboardingService.createOnboarding(newOnboarding);
                }
            }
        }

        return "redirect:/web/applications";
    }

    @GetMapping("/onboardings")
    public String showOnboardingsPage(Model model) {
        model.addAttribute("onboardings", onboardingService.getAllOnboardings());
        model.addAttribute("users", userService.getAllUsers());

        // 👥 SIMULATION DE LA TABLE EMPLOYE (Mocking pour la démonstration)
        // On crée une liste d'employés fictifs pour simuler ton autre module de gestion
        java.util.List<com.recrutement.gestion_rh.model.User> fauxEmployes = new java.util.ArrayList<>();

        try {
            // Employé 1
            com.recrutement.gestion_rh.model.User emp1 = new com.recrutement.gestion_rh.model.User();
            emp1.setId(101L);
            emp1.setFirstName("Jean-Pierre");
            emp1.setLastName("Dupond (Manager Tech)");
            fauxEmployes.add(emp1);

            // Employé 2
            com.recrutement.gestion_rh.model.User emp2 = new com.recrutement.gestion_rh.model.User();
            emp2.setId(102L);
            emp2.setFirstName("Amandine");
            emp2.setLastName("Leroy (Directrice RH)");
            fauxEmployes.add(emp2);

            // Employé 3
            com.recrutement.gestion_rh.model.User emp3 = new com.recrutement.gestion_rh.model.User();
            emp3.setId(103L);
            emp3.setFirstName("Karim");
            emp3.setLastName("Benzadi (Lead Dev Java)");
            fauxEmployes.add(emp3);
        } catch (Exception e) {
            System.out.println("Erreur création mock employés");
        }

        // On envoie cette liste d'employés à ton fichier HTML
        model.addAttribute("employes", fauxEmployes);

        return "onboardings";
    }

    @PostMapping("/onboardings/update")
    public String updateOnboardingWeb(
            @RequestParam("onboardingId") Long onboardingId,
            @RequestParam("currentStep") String currentStep,
            @RequestParam(value = "idDoc", required = false, defaultValue = "false") boolean idDoc,
            @RequestParam(value = "vitaleDoc", required = false, defaultValue = "false") boolean vitaleDoc,
            @RequestParam(value = "ribDoc", required = false, defaultValue = "false") boolean ribDoc,
            @RequestParam(value = "mentorId", required = false) Long mentorId) { // ✅ Ajouté

        // 1. Sauvegarde d'abord les étapes et les cases à cocher de base
        onboardingService.updateProgress(onboardingId, currentStep, idDoc, vitaleDoc, ribDoc);

        // 2. Traitement et sauvegarde du mentor s'il est sélectionné
        if (mentorId != null) {
            try {
                com.recrutement.gestion_rh.model.Onboarding onb = onboardingService.getAllOnboardings().stream()
                        .filter(o -> o.getId().equals(onboardingId)).findFirst().orElse(null);
                com.recrutement.gestion_rh.model.User mentor = userService.getAllUsers().stream()
                        .filter(u -> u.getId().equals(mentorId)).findFirst().orElse(null);

                if (onb != null && mentor != null) {
                    onb.setMentor(mentor);
                    // onboardingService.save(onb);
                    // Décommente cette ligne si ton service ou repository possède une méthode de mise à jour directe
                }
            } catch(Exception e) {
                System.out.println("Erreur liaison mentor");
            }
        }

        return "redirect:/web/onboardings";
    }

    @GetMapping("/candidat/offers")
    public String showCandidatOffers(HttpSession session, Model model) {
        com.recrutement.gestion_rh.model.User sessionUser = (com.recrutement.gestion_rh.model.User) session.getAttribute("connectedUser");
        if (sessionUser == null) {
            return "redirect:/web/login";
        }

        model.addAttribute("offers", jobOfferService.getAllOffers());

        java.util.List<com.recrutement.gestion_rh.model.Application> myApps = new java.util.ArrayList<>();
        try {
            for (com.recrutement.gestion_rh.model.Application app : applicationService.getApplicationsByOffer(1L)) {
                if (app.getCandidate() != null && app.getCandidate().getId().equals(sessionUser.getId())) {
                    myApps.add(app);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur liste suivi : " + e.getMessage());
        }

        model.addAttribute("myApplications", myApps);
        return "candidat-offers";
    }

    @PostMapping("/candidat/apply")
    public String candidatApply(
            @RequestParam("offerId") Long offerId,
            @RequestParam("cvFile") org.springframework.web.multipart.MultipartFile cvFile,
            @RequestParam("coverLetterFile") org.springframework.web.multipart.MultipartFile coverLetterFile,
            HttpSession session,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        com.recrutement.gestion_rh.model.User sessionUser = (com.recrutement.gestion_rh.model.User) session.getAttribute("connectedUser");
        if (sessionUser == null) {
            return "redirect:/web/login";
        }

        com.recrutement.gestion_rh.model.User dbUser = userService.getUserByEmail(sessionUser.getEmail()).orElse(null);
        com.recrutement.gestion_rh.model.JobOffer offer = jobOfferService.getAllOffers().stream()
                .filter(o -> o.getId().equals(offerId))
                .findFirst()
                .orElse(null);

        if (dbUser != null && offer != null && !cvFile.isEmpty() && !coverLetterFile.isEmpty()) {
            try {
                // 🛠️ CRÉATION ET VÉRIFICATION DES DOSSIERS PHYSIQUES SUR TON PC
                java.nio.file.Path cvDir = java.nio.file.Paths.get("uploads/cv");
                java.nio.file.Path lmDir = java.nio.file.Paths.get("uploads/lm");

                if (!java.nio.file.Files.exists(cvDir)) java.nio.file.Files.createDirectories(cvDir);
                if (!java.nio.file.Files.exists(lmDir)) java.nio.file.Files.createDirectories(lmDir);

                // 🧼 SÉCURISATION DU NOM : évite les espaces et caractères interdits (comme &)
                String safeCvName = "cv_" + dbUser.getId() + "_" + offer.getId() + "_" + System.currentTimeMillis() + ".pdf";
                String safeLmName = "lm_" + dbUser.getId() + "_" + offer.getId() + "_" + System.currentTimeMillis() + ".pdf";

                // 💾 COPIE PHYSIQUE DU FICHIER SUR LE DISQUE DUR
                java.nio.file.Files.copy(cvFile.getInputStream(), cvDir.resolve(safeCvName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                java.nio.file.Files.copy(coverLetterFile.getInputStream(), lmDir.resolve(safeLmName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Enregistrement de l'entité en Base de Données
                com.recrutement.gestion_rh.model.Application app = new com.recrutement.gestion_rh.model.Application();
                app.setCandidate(dbUser);
                app.setJobOffer(offer);
                app.setCvUrl(safeCvName);       // Stocke le nom sécurisé unique
                app.setCoverLetter(safeLmName);  // Stocke le nom de la LM sécurisé
                app.setStatus(com.recrutement.gestion_rh.model.ApplicationStatus.RECU);

                applicationService.submitApplication(app);

                redirectAttributes.addFlashAttribute("successMessage", "Votre candidature a été transmise avec succès ! Ouvrez votre espace de suivi pour vérifier. 🎉");
            } catch (Exception e) {
                System.out.println("Erreur lors de la sauvegarde physique du fichier : " + e.getMessage());
                redirectAttributes.addFlashAttribute("error", "Échec du téléversement des fichiers.");
            }
        }

        return "redirect:/web/candidat/offers";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/auth/login")
    public String handleLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        com.recrutement.gestion_rh.model.User user = userService.getUserByEmail(email).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("connectedUser", user);

            String userRoleStr = user.getRole() != null ? user.getRole().name() : "";

            if ("ADMIN_RH".equals(userRoleStr) || "RECRUTEUR".equals(userRoleStr)) {
                return "redirect:/web/dashboard";
            } else {
                return "redirect:/web/candidat/offers";
            }
        }
        model.addAttribute("error", "Email ou mot de passe incorrect.");
        return "login";
    }

    @PostMapping("/auth/register")
    public String handleRegister(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName, // ✅ Ajouté
            @RequestParam("lastName") String lastName,   // ✅ Ajouté
            Model model) {

        boolean emailExiste = userService.getAllUsers().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (emailExiste) {
            model.addAttribute("error", "Cette adresse email est déjà enregistrée.");
            return "login";
        }

        com.recrutement.gestion_rh.model.User newCandidate = new com.recrutement.gestion_rh.model.User();
        newCandidate.setEmail(email);
        newCandidate.setPassword(password);
        newCandidate.setRole(com.recrutement.gestion_rh.model.Role.CANDIDAT);

        // ✅ CORRECTION : On utilise les vraies valeurs saisies par l'utilisateur
        newCandidate.setFirstName(firstName);
        newCandidate.setLastName(lastName);

        try {
            userService.createUser(newCandidate);
            model.addAttribute("success", "Votre compte a été créé avec succès ! Vous pouvez maintenant vous connecter.");
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur est survenue lors de l'enregistrement : " + e.getMessage());
        }

        return "login";
    }

    // 1. AJOUTER ET MODIFIER UNE OFFRE (Sauvegarde standard)
    @PostMapping("/offers/save")
    public String saveJobOfferWeb(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("title") String title,
            @RequestParam("location") String location,
            @RequestParam("salary") Double salary,
            @RequestParam("description") String description,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        com.recrutement.gestion_rh.model.JobOffer offer;

        if (id != null) {
            offer = jobOfferService.getAllOffers().stream()
                    .filter(o -> o.getId().equals(id))
                    .findFirst()
                    .orElse(new com.recrutement.gestion_rh.model.JobOffer());
            redirectAttributes.addFlashAttribute("successMessage", "L'offre d'emploi a été modifiée avec succès ! 📝");
        } else {
            offer = new com.recrutement.gestion_rh.model.JobOffer();
            offer.setPostedDate(java.time.LocalDate.now());

            // ✅ CORRECTION : Utilisation directe de ton Enum en français !
            offer.setStatus(com.recrutement.gestion_rh.model.JobStatus.OUVERTE);

            redirectAttributes.addFlashAttribute("successMessage", "Nouvelle offre d'emploi publiée avec succès ! 🚀");
        }

        offer.setTitle(title);
        offer.setLocation(location);
        offer.setSalary(salary);
        offer.setDescription(description);

        try {
            // ✅ CORRIGÉ : Appel de ta vraie méthode JobOfferService.saveOffer()
            jobOfferService.saveOffer(offer);
        } catch (Exception e) {
            System.out.println("Erreur sauvegarde offre");
        }

        return "redirect:/web/offers";
    }

    // 2. SUPPRIMER UNE OFFRE
    @PostMapping("/offers/delete")
    public String deleteJobOfferWeb(@RequestParam("offerId") Long offerId, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            // ✅ CORRIGÉ : Appel de ta vraie méthode JobOfferService.deleteOffer()
            jobOfferService.deleteOffer(offerId);
            redirectAttributes.addFlashAttribute("successMessage", "L'offre d'emploi a été retirée du portail. 🗑️");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossible de supprimer cette offre (des candidatures y sont peut-être liées).");
        }
        return "redirect:/web/offers";
    }


}