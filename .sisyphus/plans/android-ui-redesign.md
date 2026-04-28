# UI/UX Redesign Plan: EWS Akademik (FocusDen Calm System)

## 1. UI/UX Analysis of Current State
* **Cognitive Overload**: Stacking 4 identical `TextInputLayout` fields vertically creates a long, repetitive form.
* **Aesthetic Mismatch**: The current UI uses generic Material outlines, conflicting directly with the `@DESIGN.md` "FocusDen Calm System" which demands Soft Minimalism, expansive whitespace, and soft rounded shapes.
* **Color Palette Violation**: The app currently lacks the specified high-key environment (Pure White / Ghost Gray) and the Electric Teal (`#00E5BC`) focal points.

## 2. Redesign Strategy: "FocusDen Calm System" (per DESIGN.md)
* **Typography**: Implement `Plus Jakarta Sans`. Large, bold headlines (`Deep Charcoal #121212`) for high contrast against the ethereal backdrop.
* **Spatial Composition**: Use `32dp` (`container-padding`) parent margins. Group the four IPS semester inputs into a crisp 2x2 grid to reduce vertical scrolling and promote the "airy" feel.
* **Input Styling**: Fields must use a Ghost Gray (`#F8FAFB`) background with a `12px` corner radius. No default borders. On focus, add a `2px` Electric Teal (`#00E5BC`) border.
* **Primary Action (Predict Button)**: Pill-shaped (`full` rounded or `24px` radius) with an Electric Teal (`#00E5BC`) background and Deep Charcoal (`#121212`) text.
* **Depth & Elevation**: Remove all harsh borders. Apply soft ambient shadows only where necessary (Level 2 surfaces), otherwise rely on whitespace and Ghost Gray backgrounds to separate elements.

## 3. Execution Tasks

### Task 1: Foundation & Theming Update [x]
* Extract colors from `DESIGN.md` into `colors.xml`: 
  * `surface_bright` / `background`: `#f8fafb`
  * `surface_container_lowest`: `#ffffff`
  * `on_surface` (Deep Charcoal): `#191c1d` (from yaml, or `#121212` from text)
  * `primary_container` / `primary_fixed_dim` (Electric Teal): `#00e5bc` / `#00e0b8`
* Download and add `Plus Jakarta Sans` font family to `res/font/`.
* Update `themes.xml` to apply the font globally and set the window background to Pure White.

### Task 2: Layout Restructuring (`activity_main.xml`)
* Increase parent `ConstraintLayout` padding to `32dp`.
* Update the Header `TextView`: Left-aligned, `Plus Jakarta Sans` bold, `Deep Charcoal`. Add a brief, calming subtitle (e.g., "Masukkan data akademik untuk prediksi.").
* Replace the vertical `LinearLayout` for IPS scores with a `GridLayout` (2 columns, 2 rows) or nested `LinearLayout`s. Ensure `24dp` (`gutter`) spacing between the grid items.
* Redesign `RadioGroup` for gender: Replace standard radio dots with pill-shaped toggle segments or subtle cards with a 12px radius that highlight Electric Teal when selected.

### Task 3: Component Styling Implementation
* **Inputs**: Create a custom drawable `bg_input_default.xml` (Solid `#F8FAFB`, `12dp` corners) and `bg_input_focused.xml` (Solid `#F8FAFB`, `12dp` corners, `2dp` `#00E5BC` stroke). Apply these via a custom style to the `EditText`s. Remove `TextInputLayout` floating labels in favor of clean static labels above the inputs, or style the `TextInputLayout` to be completely borderless.
* **Predict Button**: Update `MaterialButton` (`btnPredict`) to have `app:cornerRadius="24dp"`, background tint `#00E5BC`, and text color `#121212`. 

## Final Verification Wave
* [ ] Verify typography is strictly `Plus Jakarta Sans`.
* [ ] Verify inputs have exactly a `12dp` (approx `12px` adjusted for mdpi) radius and turn Electric Teal on focus.
* [ ] Verify the "Predict" button is pill-shaped and Electric Teal.
* [ ] Verify the 2x2 grid for IPS inputs functions correctly on smaller screens without cramping.