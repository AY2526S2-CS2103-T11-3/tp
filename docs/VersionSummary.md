---
layout: page
title: Version Summary
---

A summary of pull requests merged in each milestone, organised by version.

* Table of Contents
{:toc}

---

## v1.2

### Features & Code Changes

| PR | Description |
|----|-------------|
| [#51](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/51) | Renamed the application and data model from *AddressBook* to *ClinicBook* (package rename, storage file, UI title). |
| [#52](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/52) | Defined core ClinicBook domain classes: `Patient`, `Doctor`, `Pharmacist`, `Diagnosis`, `Prescription`. |
| [#53](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/53) | Added surrogate ID field to `Person` / `Patient` to uniquely identify records. |
| [#57](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/57) | Extended the `find` command to support searching by phone number (`find p/PHONE`). |
| [#62](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/62) | Added partial JSON persistence for `Patient`, `Diagnosis`, and `Prescription` to prepare for upcoming features. |
| [#65](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/65) | Introduced `get-history` command scaffold with NRIC format validation (`get-history nric/NRIC`). |

### Documentation

| PR | Description |
|----|-------------|
| [#41](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/41) | Updated README and site-wide settings to reflect the ClinicBook product name. |
| [#43](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/43) | Updated user profiles and user stories in the Developer Guide. |
| [#44](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/44) | Updated Use Cases, NFR, and Glossary sections of the Developer Guide. |
| [#55](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/55) | Further updates to Use Cases, NFR, and Glossary. |
| [#58](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/58) | Added Use Cases, NFR, and Glossary entries for clinic-specific workflows. |
| [#60](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/60) | Revised Use Cases, NFR, and Glossary in the Developer Guide. |

---

## v1.3

### Features & Code Changes

| PR | Description |
|----|-------------|
| [#66](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/66) | Added `add-doc` command to register a doctor (name, phone, email). |
| [#67](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/67) | Completed `get-history` command — retrieves a patient's full medical history by NRIC, with audit logging. |
| [#68](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/68) | Extended the `find` command to support searching by NRIC (`find nric/NRIC`). |
| [#69](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/69) | Added `diagnosis` command to attach a diagnosis (symptoms, visit date, diagnosing doctor) with optional prescription lines to a patient. |
| [#70](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/70) | Added `add-patient` command to register a patient with NRIC, DOB, sex, allergies, email, phone, and address. |
| [#75](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/75) | Updated the Help window link to point to the ClinicBook User Guide instead of the AB3 guide. |
| [#79](https://github.com/AY2526S2-CS2103-T11-3/tp/pull/79) | Added `add-pharmacist` command to register a pharmacist (name, email, phone). |
