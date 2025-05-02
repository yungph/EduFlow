document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    const container = document.querySelector('.container');
    const logoContainer = document.querySelector('.logo-container');
    const socialIcons = document.querySelectorAll('.social-icon');

    // Initial animations
    gsap.from(container, { duration: 0.8, opacity: 0, y: 20, ease: "power2.out" });
    gsap.from(logoContainer, { duration: 1, x: -50, opacity: 0, delay: 0.3, ease: "back.out(1.2)" });
    gsap.from('.login-form h2', { duration: 0.8, y: -30, opacity: 0, delay: 0.6, ease: "elastic.out(1, 0.5)" });
    gsap.from('.form-group', { duration: 0.6, y: 20, opacity: 0, stagger: 0.1, delay: 0.8, ease: "power2.out" });
    gsap.from('.submit-btn', { duration: 0.8, scale: 0.8, opacity: 0, delay: 1.4, ease: "back.out(1.2)" });
    gsap.from(socialIcons, { duration: 0.6, y: 20, opacity: 0, stagger: 0.1, delay: 1.6, ease: "power2.out" });

    socialIcons.forEach(icon => {
        icon.addEventListener('mouseenter', () => {
            gsap.to(icon, {
                duration: 0.3,
                y: -5,
                scale: 1.1,
                boxShadow: "0 5px 15px rgba(0,0,0,0.3)",
                ease: "power2.out"
            });
        });
        icon.addEventListener('mouseleave', () => {
            gsap.to(icon, {
                duration: 0.3,
                y: 0,
                scale: 1,
                boxShadow: "none",
                ease: "power2.out"
            });
        });
    });

    const emailInput = document.getElementById('loginEmail');
    const passwordInput = document.getElementById('loginPassword');
    const rememberCheckbox = document.getElementById('rememberMe');

    [emailInput, passwordInput].forEach(input => {
        input.addEventListener('focus', () => {
            gsap.to(input, {
                duration: 0.3,
                boxShadow: `0 0 0 2px var(--accent-color)`,
                ease: "power2.out"
            });
            const icon = input.previousElementSibling;
            gsap.to(icon, {
                duration: 0.3,
                color: "var(--accent-color)",
                scale: 1.2,
                ease: "power2.out"
            });
        });
        input.addEventListener('blur', () => {
            gsap.to(input, {
                duration: 0.3,
                boxShadow: "none",
                ease: "power2.out"
            });
            const icon = input.previousElementSibling;
            gsap.to(icon, {
                duration: 0.3,
                color: "var(--secondary-color)",
                scale: 1,
                ease: "power2.out"
            });
        });
    });

    rememberCheckbox.addEventListener('change', function () {
        gsap.to(this, {
            duration: 0.3,
            boxShadow: this.checked ? `0 0 0 2px var(--accent-color)` : "none",
            ease: "power2.out"
        });
    });

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        clearErrors();

        let isValid = true;

        if (!emailInput.value) {
            showError(emailInput, 'Email is required');
            shakeElement(emailInput);
            isValid = false;
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value)) {
            showError(emailInput, 'Please enter a valid email');
            shakeElement(emailInput);
            isValid = false;
        }

        if (!passwordInput.value) {
            showError(passwordInput, 'Password is required');
            shakeElement(passwordInput);
            isValid = false;
        }
        // else if (passwordInput.value.length < 8) {
        //     showError(passwordInput, 'Password must be at least 8 characters');
        //     shakeElement(passwordInput);
        //     isValid = false;
        // }

        if (isValid) {
            if (rememberCheckbox.checked) {
                localStorage.setItem('rememberedEmail', emailInput.value);
            } else {
                localStorage.removeItem('rememberedEmail');
            }

            // Send login request to backend
            fetch('/api/auth/new-login', { // updated endpoint
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: emailInput.value,
                    password: passwordInput.value
                })
            })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => {
                            throw new Error(text || "Login failed. Please check your credentials.");
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    // Save token/role if returned
                    if (data.token) localStorage.setItem('token', data.token);
                    if (data.role) localStorage.setItem('userRole', data.role);

                    gsap.to(form, {
                        duration: 0.5,
                        opacity: 0,
                        y: -20,
                        ease: "power2.in",
                        onComplete: showSuccessMessage
                    });
                })
                .catch(error => {
                    showError(passwordInput, error.message);
                    shakeElement(emailInput);
                    shakeElement(passwordInput);
                });
        }
    });


    function showSuccessMessage() {
        form.innerHTML = `
            <div class="success-message">
                <i class="fas fa-check-circle"></i>
                <h3>Login Successful!</h3>
                <p>Redirecting to your dashboard...</p>
                <div class="loader"></div>
            </div>
        `;

        gsap.from('.success-message', { duration: 0.8, opacity: 0, y: 30, ease: "back.out(1.2)" });
        gsap.from('.success-message i', { duration: 1, scale: 0, rotation: 360, ease: "elastic.out(1, 0.5)" });

        gsap.to(document.querySelector('.loader'), {
            duration: 1,
            rotation: 360,
            repeat: -1,
            ease: "none"
        });

        setTimeout(() => {
            gsap.to(container, {
                duration: 0.5,
                opacity: 0,
                y: -20,
                ease: "power2.in",
                onComplete: () => {
                    window.location.href = '/index-page';
                }
            });
        }, 2000);
    }

    function shakeElement(element) {
        gsap.to(element, {
            duration: 0.6,
            x: [-10, 10, -8, 8, -5, 5, 0],
            ease: "power1.out"
        });
    }

    function showError(input, message) {
        const formGroup = input.closest('.form-group');
        formGroup.classList.add('error');

        let errorElement = formGroup.querySelector('.error-message');
        if (!errorElement) {
            errorElement = document.createElement('div');
            errorElement.className = 'error-message';
            formGroup.appendChild(errorElement);

            gsap.from(errorElement, {
                duration: 0.3,
                opacity: 0,
                y: -10,
                ease: "power2.out"
            });
        }

        errorElement.textContent = message;
    }

    function clearErrors() {
        document.querySelectorAll('.form-group').forEach(group => {
            group.classList.remove('error');
            const errorElement = group.querySelector('.error-message');
            if (errorElement) {
                gsap.to(errorElement, {
                    duration: 0.2,
                    opacity: 0,
                    y: -10,
                    ease: "power2.out",
                    onComplete: () => errorElement.remove()
                });
            }
        });
    }
});
