import pandas as pd
import matplotlib.pyplot as plt

# Lire les données à partir du fichier CSV
data = pd.read_csv('defaultArrivalRatesm.csv', header=None, names=['time', 'arrival_rate'])

# Tracer les données
plt.figure(figsize=(10, 6))
plt.plot(data['time'], data['arrival_rate'], markersize=3, linestyle='-', color='b', linewidth=1)  # Ligne plus fine et marqueurs plus petits

# Ajouter des titres et des étiquettes
plt.title('Arrival Rate of Events at Time t (λ_p^t)', fontsize=16)
plt.xlabel('Time (t)', fontsize=14)
plt.ylabel('Arrival Rate (events/sec) (λ_p^t)', fontsize=14)

# Afficher le graphique
plt.grid(True)
plt.show()