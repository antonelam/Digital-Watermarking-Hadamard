# Digital-Watermarking-Hadamard

# This is only one part of the project
# Parts that are missing: gui code, libraries, code that I used but it's created by other people
# If you try to run this code, it won't work

# This is not intended for active changing (there are to many parts missing)
# If you want to work on this code, you can download it I change localy

# Description:
# This code implements essential part of loading images, divading them into blocks and converting them from RGB to YUV color space
# Images are converted to Hadamard coefficients and processed using Hadamard transformation
# One of the images is a watermark which is breaked down into coefficients and then inserted into the second image
# Watermark is invisible
# This code has three parts: inserting (putting a watermark), extracting (finding a watermark) and attacking (attacking watermarked image)
# Attacking part used javaxt-core Image class (there can be a confusion with java Image class) - http://www.javaxt.com/documentation/?jar=javaxt-core&package=javaxt.io&class=Image
