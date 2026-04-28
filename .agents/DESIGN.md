---
name: FocusDen Calm System
colors:
  surface: '#f8fafb'
  surface-dim: '#d8dadb'
  surface-bright: '#f8fafb'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f4f5'
  surface-container: '#eceeef'
  surface-container-high: '#e6e8e9'
  surface-container-highest: '#e1e3e4'
  on-surface: '#191c1d'
  on-surface-variant: '#3b4a44'
  inverse-surface: '#2e3132'
  inverse-on-surface: '#eff1f2'
  outline: '#6b7b74'
  outline-variant: '#b9cac3'
  surface-tint: '#006b57'
  primary: '#006b57'
  on-primary: '#ffffff'
  primary-container: '#00e5bc'
  on-primary-container: '#00614f'
  inverse-primary: '#00e0b8'
  secondary: '#5f5e5e'
  on-secondary: '#ffffff'
  secondary-container: '#e5e2e1'
  on-secondary-container: '#656464'
  tertiary: '#505f76'
  on-tertiary: '#ffffff'
  tertiary-container: '#bccce6'
  on-tertiary-container: '#47566c'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#42fdd3'
  primary-fixed-dim: '#00e0b8'
  on-primary-fixed: '#002019'
  on-primary-fixed-variant: '#005141'
  secondary-fixed: '#e5e2e1'
  secondary-fixed-dim: '#c8c6c5'
  on-secondary-fixed: '#1c1b1b'
  on-secondary-fixed-variant: '#474646'
  tertiary-fixed: '#d3e4fe'
  tertiary-fixed-dim: '#b7c8e1'
  on-tertiary-fixed: '#0b1c30'
  on-tertiary-fixed-variant: '#38485d'
  background: '#f8fafb'
  on-background: '#191c1d'
  surface-variant: '#e1e3e4'
typography:
  display:
    fontFamily: Plus Jakarta Sans
    fontSize: 48px
    fontWeight: '800'
    lineHeight: '1.1'
    letterSpacing: -0.02em
  h1:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: -0.01em
  h2:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: '1.3'
    letterSpacing: -0.01em
  h3:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '600'
    lineHeight: '1.4'
    letterSpacing: '0'
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: '0'
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
    letterSpacing: '0'
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: '1.5'
    letterSpacing: '0'
  label-bold:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: '1'
    letterSpacing: 0.05em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1'
    letterSpacing: '0'
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 12px
  md: 24px
  lg: 40px
  xl: 64px
  container-padding: 32px
  gutter: 24px
---

## Brand & Style
The design system is anchored in "Digital Zen"—a philosophy that balances high-energy motivation with a peaceful, clutter-free environment. Targeted at Gen Z, the aesthetic avoids corporate stiffness in favor of a "Soft Minimalist" approach. It uses expansive whitespace to reduce cognitive load while employing a singular, vibrant accent to drive action and focus.

The emotional response should be one of immediate clarity. By stripping away unnecessary decorative elements and focusing on intentionality, this design system transforms productivity from a chore into a curated experience. It is clean, airy, and optimistic.

## Colors
The palette is dominated by **White (#FFFFFF)** and **Ghost Gray (#F8FAFB)** to create a layered, ethereal backdrop. This high-key environment is punctuated by **Electric Teal (#00E5BC)**, which serves as the primary call-to-action and progress indicator, symbolizing energy and "flow."

Typography and core iconography utilize **Deep Charcoal (#121212)** to ensure maximum readability and a sophisticated high-contrast feel. Semantic colors (success, error, warning) should be used sparingly and desaturated to maintain the calm atmosphere, only gaining vibrance when user intervention is required.

## Typography
This design system leverages **Plus Jakarta Sans** for its friendly yet modern geometric proportions. The typographic scale is designed for high contrast: large, bold headlines provide clear entry points for the eye, while generous line heights in the body text promote a relaxed reading pace.

To maintain the "airy" feel, tracking (letter spacing) is slightly tightened on large display type to keep it cohesive, while labels use slightly increased tracking to ensure legibility at small sizes. Use the "Deep Charcoal" color for all primary text to maintain the high-contrast aesthetic against the white background.

## Layout & Spacing
The layout follows a **Fluid Grid** model with an emphasis on oversized gutters and margins to create a sense of breathing room. A 12-column grid is used for desktop, but elements are encouraged to float within "islands" rather than spanning the full width of the viewport.

The spacing rhythm is built on an 8px base unit. Vertical rhythm is intentionally loose; sections should be separated by `lg` (40px) or `xl` (64px) values to prevent the UI from feeling cramped. Content should be centered with a maximum container width of 1280px to maintain focus.

## Elevation & Depth
Depth in the design system is achieved through **Ambient Shadows** and **Tonal Layering**. We avoid harsh borders. Surfaces are defined by very soft, diffused shadows with a large blur radius (e.g., 20-30px) and low opacity (5-8%), using a slight teal-tinted neutral color rather than pure black for the shadow.

- **Level 0 (Background):** Pure White (#FFFFFF).
- **Level 1 (Secondary Surfaces):** Ghost Gray (#F8FAFB) with no shadow, used for inset areas like sidebar backgrounds.
- **Level 2 (Cards/Modules):** White surfaces with "Soft" ambient shadows to appear floating.
- **Level 3 (Modals/Popovers):** White surfaces with slightly more pronounced shadows and a backdrop blur (10px) on the layers beneath.

## Shapes
The shape language is consistently **Rounded (Level 2)**. This removes "visual sharpness" from the UI, contributing to the calm and approachable brand personality. 

- **Standard Elements:** 0.5rem (8px) for small components like checkboxes or tags.
- **Main Cards:** 1rem (16px) for the primary content containers.
- **Buttons & Large Containers:** 1.5rem (24px) to create a friendly, "pebbled" appearance.
- **Interactive States:** On hover, shapes may slightly increase their roundedness or scale by 1-2% to provide tactile feedback.

## Components
- **Buttons:** Primary buttons are pill-shaped with an Electric Teal background and Charcoal text for high contrast. Secondary buttons use a Ghost Gray fill with Charcoal text. 
- **Cards:** White backgrounds, 16px corner radius, and a soft ambient shadow. Cards should never have borders unless they are in an "inactive" or "empty" state.
- **Inputs:** Fields use a Ghost Gray background with a 12px corner radius. On focus, the background stays gray, but a 2px Electric Teal border appears.
- **Chips/Tags:** Used for categorization, these should be small, fully rounded (pill), and use desaturated versions of the accent color to stay subtle.
- **Lists:** Items should be separated by whitespace and subtle 1px gray dividers rather than boxes, keeping the vertical flow light.
- **Progress Indicators:** Use the Electric Teal exclusively. Progress bars should have a rounded track and a glowing effect to motivate the user.
- **Empty States:** Use light gray illustrations with high-contrast Plus Jakarta Sans typography to guide the user back to focus.