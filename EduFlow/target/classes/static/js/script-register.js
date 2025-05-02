document.addEventListener('DOMContentLoaded', function() {
    // Initialize elements
    const form = document.getElementById('registrationForm');
    const container = document.querySelector('.container');
    const logoContainer = document.querySelector('.logo-container');
    const formTitle = document.querySelector('.registration-form h2');
    const formGroups = document.querySelectorAll('.form-group');
    const submitBtn = document.querySelector('.submit-btn');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const passwordStrengthBars = document.querySelectorAll('.password-strength .strength-bar');
    const termsCheckbox = document.getElementById('terms');
    const allInputs = document.querySelectorAll('input:not([type="checkbox"])');

    // Initial animations
    gsap.from(container, { duration: 0.8, opacity: 0, y: 20, ease: "power2.out" });
    gsap.from(logoContainer, { duration: 1, x: -50, opacity: 0, delay: 0.3, ease: "back.out(1.2)" });
    gsap.from(formTitle, { duration: 0.8, y: -30, opacity: 0, delay: 0.6, ease: "elastic.out(1, 0.5)" });
    gsap.from(formGroups, { duration: 0.6, y: 20, opacity: 0, stagger: 0.1, delay: 0.8, ease: "power2.out" });
    gsap.from(submitBtn, { duration: 0.8, scale: 0.8, opacity: 0, delay: 1.4, ease: "back.out(1.2)" });

    // Input focus animations
    allInputs.forEach(input => {
        input.addEventListener('focus', () => {
            gsap.to(input, { duration: 0.3, boxShadow: '0 0 0 2px #4a90e2', ease: "power2.out" });
            const icon = input.previousElementSibling;
            if (icon && icon.classList.contains('input-icon')) {
                gsap.to(icon, { duration: 0.3, color: "#4a90e2", scale: 1.2, ease: "power2.out" });
            }
        });

        input.addEventListener('blur', () => {
            gsap.to(input, { duration: 0.3, boxShadow: 'none', ease: "power2.out" });
            const icon = input.previousElementSibling;
            if (icon && icon.classList.contains('input-icon')) {
                gsap.to(icon, { duration: 0.3, color: "#777", scale: 1, ease: "power2.out" });
            }
        });
    });

    // Password strength indicator
    passwordInput.addEventListener('input', function() {
        const password = this.value;
        let strength = 0;

        if (password.length >= 8) strength++;
        if (/[A-Z]/.test(password)) strength++;
        if (/[0-9]/.test(password)) strength++;
        if (/[^A-Za-z0-9]/.test(password)) strength++;

        passwordStrengthBars.forEach((bar, index) => {
            if (index < strength) {
                gsap.to(bar, {
                    duration: 0.3,
                    backgroundColor: getStrengthColor(strength),
                    scaleY: 1.2,
                    yoyo: true,
                    repeat: 1,
                    ease: "power1.out"
                });
            } else {
                bar.style.backgroundColor = '#e0e0e0';
            }
        });
    });

    function getStrengthColor(strength) {
        switch(strength) {
            case 1: return '#ff7675'; // Weak
            case 2: return '#fdcb6e'; // Medium
            case 3: return '#55efc4'; // Good
            case 4: return '#00b894'; // Strong
            default: return '#e0e0e0';
        }
    }

    // Form validation
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        clearErrors();

        let isValid = true;

        // Disable form elements during submission
        form.querySelectorAll("input, select, button").forEach(el => el.disabled = true);
        submitBtn.textContent = "Registering...";

        // Validate required fields
        allInputs.forEach(input => {
            if (!input.value.trim()) {
                showError(input, 'This field is required');
                shakeElement(input);
                isValid = false;
            }
        });

        // Validate email format
        const emailInput = document.getElementById('email');
        if (emailInput.value && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value)) {
            showError(emailInput, 'Please enter a valid email');
            shakeElement(emailInput);
            isValid = false;
        }

        // Validate password match
        if (passwordInput.value !== confirmPasswordInput.value) {
            showError(confirmPasswordInput, 'Passwords do not match');
            shakeElement(confirmPasswordInput);
            isValid = false;
        }

        // Validate password length
        if (passwordInput.value.length < 8) {
            showError(passwordInput, 'Password must be at least 8 characters');
            shakeElement(passwordInput);
            isValid = false;
        }

        // Validate role input (Student or Instructor)
        const roleInput = document.getElementById("accountType").value.trim().toLowerCase();
        if (roleInput !== "student" && roleInput !== "instructor") {
            showError(document.getElementById("accountType"), "Please enter 'student' or 'instructor' as your account type");
            shakeElement(document.getElementById("accountType"));
            isValid = false;
        }

        // Validate terms checkbox
        if (!termsCheckbox.checked) {
            const termsContainer = termsCheckbox.closest('.checkbox-group');
            showError(termsContainer, 'You must accept the terms');
            shakeElement(termsContainer);
            isValid = false;
        }

        if (isValid) {
            const userData = {
                name: document.getElementById("fullName").value,
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
                role: roleInput // Use the typed role value
            };

            fetch("/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(userData)
            })
                .then(response => {
                    if (response.ok) {
                        // Animate and show success
                        gsap.to(form, {
                            duration: 0.5,
                            opacity: 0,
                            y: -20,
                            ease: "power2.in",
                            onComplete: showSuccessMessage
                        });
                    } else {
                        return response.json().then(data => {
                            throw new Error(data.message || "Registration failed");
                        });
                    }
                })
                .catch(error => {
                    alert("Error: " + error.message);
                    gsap.to(form, { duration: 0.3, opacity: 1, y: 0 }); // bounce back
                    form.querySelectorAll("input, select, button").forEach(el => el.disabled = false);
                    submitBtn.textContent = "Register Now";
                });
        }
    });

    function showSuccessMessage() {
        form.innerHTML = `
            <div class="success-message">
                <i class="fas fa-check-circle"></i>
                <h3>Registration Successful!</h3>
                <p>Welcome to EduFlow Academy. Check your email to verify your account.</p>
                <button onclick="window.location.reload()" class="submit-btn pulse-animation">
                    Back to Registration
                </button>
            </div>
        `;

        // Animate success message
        gsap.from('.success-message', { duration: 0.8, opacity: 0, y: 30, ease: "back.out(1.2)" });
        gsap.from('.success-message i', { duration: 1, scale: 0, rotation: 360, ease: "elastic.out(1, 0.5)" });
    }

    function shakeElement(element) {
        gsap.to(element, { duration: 0.6, x: [-10, 10, -8, 8, -5, 5, 0], ease: "power1.out" });
    }

    function showError(element, message) {
        const formGroup = element.closest('.form-group') || element.closest('.checkbox-group');
        formGroup.classList.add('error');

        let errorElement = formGroup.querySelector('.error-message');
        if (!errorElement) {
            errorElement = document.createElement('div');
            errorElement.className = 'error-message';
            formGroup.appendChild(errorElement);
        }

        errorElement.textContent = message;
        gsap.from(errorElement, { duration: 0.3, opacity: 0, y: -10, ease: "power2.out" });
    }

    function clearErrors() {
        document.querySelectorAll('.form-group, .checkbox-group').forEach(group => {
            group.classList.remove('error');
            const errorElement = group.querySelector('.error-message');
            if (errorElement) {
                gsap.to(errorElement, {
                    duration: 0.3,
                    opacity: 0,
                    onComplete: () => errorElement.remove()
                });
            }
        });
    }
});
