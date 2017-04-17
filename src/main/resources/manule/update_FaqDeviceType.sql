update sprki_cms_contentfaq
INNER JOIN sprki_labthink_devices On sprki_cms_contentfaq.devices = sprki_labthink_devices.id
set sprki_cms_contentfaq.devicetype = sprki_labthink_devices.device_type;
