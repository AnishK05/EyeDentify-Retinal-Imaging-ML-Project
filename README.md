Research Project Summary: 

This research project devloped an inexpensive, non-mydriatic retinal imaging device combined with deep learning algorithms for broad-spectrum disease diagnosis. The goal was to create an accessible and accurate tool for diagnosing 24 different ophthalmic and neurodegenerative (i.e., Alzheimer's, Parkinson's) diseases by utilizing retinal biomarkers. The study involved building a non-mydriatic infrared imaging device (via Raspberry Pi and an infrared camera) and integrating it with several deep learning models to analyze and diagnose retinal images.

The research utilized a limited dataset, preprocessing techniques like Gaussian blur, grayscale, histogram equalization, and normalization to enhance model accuracy. Multiple deep learning architectures, including a multi-layer feedforward neural network and 3x3 Conv2D models, were evaluated. The optimized models demonstrated significant improvements in diagnostic accuracy, with the categorical classification model achieving an accuracy of 99.4% (overall efficacy rate of 0.9 - 0.97). An Android mobile app was developed to host the ML models and provide real-time disease diagnosis.

Statistical analysis confirmed the reliability of the models, showing a statistically significant improvement over traditional diagnostic methods. This project demonstrates the potential of combining low-cost hardware and with ML/AI to make advanced healthcare diagnostics more accessible worldwide.

Future work includes expanding datasets, refining deep learning models, integrating additional features for locating treatment centers, and enhancing the device's hardware and software for real-world application.

Published 40-page research paper in the Social Science Research Network (SSRN): https://papers.ssrn.com/sol3/papers.cfm?abstract_id=4438594

Group Members: Anish Kalra, Trishay Naman, Dhroov Pathare

Note: Raspberry Pi scripts (which were used for deep learning model integration with raspberry pi imaging device) are not available in this repo
