# Project 3 Implementation Report

**Student:** Prakriti Sathapa  
**Course:** CSCI 430  
**Date:** December 6, 2025

## What Has Been Implemented

This project implements three new features for the Java drawing program:

**1. Circle Drawing Feature (+15 points)**  
Allows users to create circles by clicking two points to define the diameter. Includes preview functionality where the circle follows the mouse during creation.

**2. Polygon Drawing Feature**  
Enables creation of polygons through multiple left-clicks to add vertices, with right-click to complete the shape.

**3. Move Item Feature**  
Provides drag-and-drop functionality to move selected items. Users must first select an item using the Select button, then use Move to drag it to a new position.

All features include full undo/redo support using the Command pattern and integrate seamlessly with the existing drawing program architecture.

## Completed Testing Table

| Feature | Test Case | Expected Result | Actual Result | Status |
|---------|-----------|----------------|---------------|---------|
| **Circle Drawing** | Click two points for diameter | Circle appears with specified diameter | Circle created and persists on screen | ✅ Pass |
| Circle Drawing | Preview during creation | Circle follows mouse between clicks | Preview circle visible and responsive | ✅ Pass |
| Circle Drawing | Undo circle creation | Circle disappears from canvas | Circle removed successfully | ✅ Pass |
| Circle Drawing | Redo circle creation | Circle reappears on canvas | Circle restored successfully | ✅ Pass |
| **Polygon Drawing** | Left-click to add vertices | Points added to polygon | Vertices added correctly | ✅ Pass |
| Polygon Drawing | Right-click to complete | Polygon finalizes and appears | Polygon completed and rendered | ✅ Pass |
| Polygon Drawing | Undo polygon creation | Polygon disappears from canvas | Polygon removed successfully | ✅ Pass |
| Polygon Drawing | Redo polygon creation | Polygon reappears on canvas | Polygon restored successfully | ✅ Pass |
| **Move Items** | Click Move without selection | Error message displayed | "Please select an item first" dialog shown | ✅ Pass |
| Move Items | Select item then move | Item follows mouse drag | Selected item moves with cursor | ✅ Pass |
| Move Items | Undo move operation | Item returns to original position | Item position restored | ✅ Pass |
| Move Items | Redo move operation | Item moves to new position again | Item position updated | ✅ Pass |

**Video Demonstration Link:** [To be added]