(() => {
    const parseDate = (dateStr, format = "DD/MM/YYYY") => {
      const formatParts = format.split(/[^A-Z]/);
      const dateParts = dateStr.split(/[^0-9]/);

      const dateMap = {};
      formatParts.forEach((part, i) => {
        dateMap[part] = parseInt(dateParts[i], 10);
      });

      const day = dateMap["DD"] || 1;
      const month = (dateMap["MM"] || 1) - 1;
      const year = dateMap["YYYY"] || 1970;

      return new Date(year, month, day);
    };

    document.querySelectorAll('input[type="date"]').forEach(input => {
        const rawAttrValue = input.getAttribute('value');
        if(!rawAttrValue || rawAttrValue.trim().length == 0) return;

        try {
            let parsedDate = new Date(rawAttrValue);
            if(!parsedDate || isNaN(parsedDate)) parsedDate = parseDate(parsedDate);

            if(!parsedDate || isNaN(parsedDate)) throw new Error("Invalid date format");

            const year = parsedDate.getFullYear().toString().padStart(4, '0');
            const month = (parsedDate.getMonth() + 1).toString().padStart(2, '0');
            const day = parsedDate.getDate().toString().padStart(2, '0');

            const computedValue = `${year}-${month}-${day}`;
            input.setAttribute('value', computedValue);
            input.value = computedValue;
        }
        catch(err) {
            console.log(`Failed to parse date input value "${rawAttrValue}", reason : ${err}`);
        }
    });
})();