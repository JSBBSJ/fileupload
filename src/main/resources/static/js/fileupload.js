
function uploadImage(url, formData){
	return fetch(url,{
		method: "POST",
		body: formData
	})
	//.then(response => response.text())
	.then(response => response.json())
	.catch(error => {
		console.log('Error :' , error );
		throw error;
	})
	//.finally()
	;	
}

function fileupload(input) {
	const files = input.files;
	if (files.length < 1) {
		console.log("파일이 선택되지 않았어요");
		return;
	}

	//console.log(files[0]);
	//const fileName=files[0].name;

	const fileType = files[0].type;
	if (!fileType.startsWith('image/')) {
		alert("이미지 파일이 아닙니다.");
		input.value = '';
		return;
	}

	const fileSize = files[0].size;
	if (fileSize > 2 * 1024 * 1024) {
		alert("파일용량제한:2MB이내의 파일을 사용하세요" + fileSize);
		input.value = '';
		return;
	}
	
	var formData=new FormData();
	formData.append("itemFile",input.files[0]);
	
	//미리 temp 업로드
	uploadImage("/admin/fileupload",formData)
	.then(result=>{
		console.log(result);
		const url = result.url;
		const bucketkey = result.bucketkey;
		const orgName = result.orgName;
		
		input.parentElement.style.backgroundImage=`url(${url})`;
		input.parentElement.style.backgroundColor="transparent";
		
		let bucketkeyInput=input.nextElementSibling;
		bucketkeyInput.value=bucketkey;
		let orgNameInput = bucketkeyInput.nextElementSibling; 
		orgNameInput.value=orgName;
			
	})
	
	.catch(error=>{
		alert("파일업로드 실패! : "+ error.response.status);
	});
} 


