import csv

# Chemin vers le fichier CSV
fichier_csv = 'scaling_duration.csv'

# Initialiser la somme et le compteur
somme = 0
compteur = 0

# Ouvrir le fichier CSV
with open(fichier_csv, mode='r') as fichier:
    lecteur_csv = csv.reader(fichier)
    next(lecteur_csv)  # Optionnel: sauter l'en-tête si le fichier en contient un
    for ligne in lecteur_csv:
        # Convertir la valeur de la deuxième colonne en flottant et l'ajouter à la somme
        valeur = float(ligne[1])
        somme += valeur
        compteur += 1

# Calculer la moyenne
moyenne = somme / compteur if compteur else 0

# Afficher la moyenne
print(f'La moyenne des valeurs de la deuxième colonne est: {moyenne}')