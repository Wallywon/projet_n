document.addEventListener('DOMContentLoaded', function() {
    // API endpoints
    const API = {
      PARSER: {
        BASE: 'http://localhost:8082/parser_api/api/students',
        GET_ALL: 'http://localhost:8082/parser_api/api/students/',
        CHECK: 'http://localhost:8082/parser_api/api/students/check/',
        UPLOAD: 'http://localhost:8082/parser_api/api/students/'
      },
      CORE: {
        BASE: 'http://localhost:8081/api/messages',
        GET_BY_STUDENT: 'http://localhost:8081/api/messages/student/',
        GET_BY_ID: 'http://localhost:8081/api/messages/',
        CREATE: 'http://localhost:8081/api/messages/post',
        UPDATE: 'http://localhost:8081/api/messages/',
        DELETE: 'http://localhost:8081/api/messages/'
      }
    };
  
    // DOM Elements - Navigation
    const navLinks = document.querySelectorAll('nav a');
    const sections = document.querySelectorAll('.page-section');
    const featureButtons = document.querySelectorAll('.feature-card .btn');
  
    // DOM Elements - Student Upload
    const uploadForm = document.getElementById('upload-form');
    const fileInput = document.getElementById('file-input');
    const uploadBtn = document.getElementById('upload-btn');
    const uploadMessage = document.getElementById('upload-message');
    const uploadedStudentsTable = document.getElementById('uploaded-students-table').querySelector('tbody');
    const parsedStudents = document.getElementById('parsed-students');
  
    // DOM Elements - Student List
    const refreshStudentsBtn = document.getElementById('refresh-students');
    const studentsTable = document.getElementById('students-table').querySelector('tbody');
    const listMessage = document.getElementById('list-message');
  
    // DOM Elements - Tabs
    const tabButtons = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');
  
    // DOM Elements - Student Messages
    const messageStudentId = document.getElementById('message-student-id');
    const fetchMessagesBtn = document.getElementById('fetch-messages');
    const messagesTable = document.getElementById('messages-table').querySelector('tbody');
    const messagesMessage = document.getElementById('messages-message');
    const newMessageBtn = document.getElementById('new-message-btn');
    const messageForm = document.getElementById('message-form');
    const messageEditForm = document.getElementById('message-edit-form');
    const messageFormTitle = document.getElementById('message-form-title');
    const messageId = document.getElementById('message-id');
    const messageStudent = document.getElementById('message-student');
    const messageText = document.getElementById('message-text');
    const messageRead = document.getElementById('message-read');
    const cancelMessageBtn = document.getElementById('cancel-message');
  
    // DOM Elements - Check Student
    const checkStudentNumber = document.getElementById('check-student-number');
    const checkStudentBtn = document.getElementById('check-student-btn');
    const checkResult = document.getElementById('check-result');
    const checkMessage = document.getElementById('check-message');
  
    // Navigation Functions
    function showSection(sectionId) {
      sections.forEach(section => {
        section.classList.remove('active');
      });
  
      navLinks.forEach(link => {
        link.classList.remove('active');
      });
  
      document.getElementById(`${sectionId}-section`).classList.add('active');
      document.getElementById(`nav-${sectionId}`).classList.add('active');
  
      // Load data for the section if needed
      if (sectionId === 'students') {
        loadStudentTabs();
      }
    }
  
    // Initialize navigation event listeners
    navLinks.forEach(link => {
      link.addEventListener('click', function(e) {
        e.preventDefault();
        const sectionId = this.id.replace('nav-', '');
        showSection(sectionId);
      });
    });
  
    // Feature card navigation buttons
    featureButtons.forEach(button => {
      button.addEventListener('click', function() {
        const sectionId = this.id.replace('goto-', '');
        showSection(sectionId);
      });
    });
  
    // Tab Functions
    function initTabs() {
      tabButtons.forEach(button => {
        button.addEventListener('click', function() {
          const tabId = this.getAttribute('data-tab');
          
          // Remove active class from all tabs
          tabButtons.forEach(tab => tab.classList.remove('active'));
          tabContents.forEach(content => content.classList.remove('active'));
          
          // Add active class to current tab
          this.classList.add('active');
          document.getElementById(tabId).classList.add('active');
        });
      });
    }
  
    function loadStudentTabs() {
      // If we're on the list tab, fetch students
      if (document.querySelector('.tab-btn[data-tab="list-tab"]').classList.contains('active')) {
        fetchStudents();
      }
    }
  
    // Helper Functions
    function showMessage(element, message, type) {
      element.textContent = message;
      element.className = 'message ' + type;
      element.classList.remove('hidden');
    }
  
    function formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString();
    }
  
    // Student Upload Functions
    function initUploadForm() {
      uploadForm.addEventListener('submit', function(e) {
        e.preventDefault();
        uploadStudentFile();
      });
    }
  
    async function uploadStudentFile() {
      if (!fileInput.files[0]) {
        showMessage(uploadMessage, 'Please select a file to upload', 'error');
        return;
      }
  
      const formData = new FormData();
      formData.append('file', fileInput.files[0]);
  
      uploadBtn.textContent = 'Uploading...';
      uploadBtn.disabled = true;
  
      try {
        const response = await fetch(API.PARSER.UPLOAD, {
          method: 'POST',
          body: formData
        });
  
        const data = await response.json();
        
        if (data.success) {
          showMessage(uploadMessage, data.message || 'Students uploaded successfully', 'success');
          
          if (data.students && data.students.length) {
            displayUploadedStudents(data.students);
          }
        } else {
          showMessage(uploadMessage, data.message || 'Failed to upload students', 'error');
        }
      } catch (error) {
        showMessage(uploadMessage, 'Error uploading file: ' + error.message, 'error');
      } finally {
        uploadBtn.textContent = 'Upload and Parse';
        uploadBtn.disabled = false;
      }
    }
  
    function displayUploadedStudents(students) {
      uploadedStudentsTable.innerHTML = '';
      
      if (students.length === 0) {
        parsedStudents.classList.add('hidden');
        return;
      }
      
      parsedStudents.classList.remove('hidden');
      
      students.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${student.firstName || ''}</td>
          <td>${student.lastName || ''}</td>
          <td>${student.studentNumber || ''}</td>
          <td>${formatDate(student.birthDate)}</td>
        `;
        uploadedStudentsTable.appendChild(row);
      });
    }
  
    // Student List Functions
    function initStudentList() {
      refreshStudentsBtn.addEventListener('click', fetchStudents);
    }
  
    async function fetchStudents() {
      refreshStudentsBtn.textContent = 'Loading...';
      refreshStudentsBtn.disabled = true;
      
      try {
        const response = await fetch(API.PARSER.GET_ALL);
        const students = await response.json();
        
        displayStudents(students);
        listMessage.classList.add('hidden');
      } catch (error) {
        showMessage(listMessage, 'Error fetching students: ' + error.message, 'error');
      } finally {
        refreshStudentsBtn.textContent = 'Refresh List';
        refreshStudentsBtn.disabled = false;
      }
    }
  
    function displayStudents(students) {
      studentsTable.innerHTML = '';
      
      if (students.length === 0) {
        showMessage(listMessage, 'No students found in the database', 'info');
        return;
      }
      
      students.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${student.firstName || ''}</td>
          <td>${student.lastName || ''}</td>
          <td>${student.studentNumber || ''}</td>
          <td>${formatDate(student.birthDate)}</td>
        `;
        studentsTable.appendChild(row);
      });
    }
  
    // Student Messages Functions
    function initMessagesSection() {
      fetchMessagesBtn.addEventListener('click', fetchMessages);
      newMessageBtn.addEventListener('click', showNewMessageForm);
      cancelMessageBtn.addEventListener('click', hideMessageForm);
      messageEditForm.addEventListener('submit', saveMessage);
    }
  
    async function fetchMessages() {
      const studentId = messageStudentId.value.trim();
      
      if (!studentId) {
        showMessage(messagesMessage, 'Please enter a student number', 'error');
        return;
      }
      
      fetchMessagesBtn.textContent = 'Loading...';
      fetchMessagesBtn.disabled = true;
      
      try {
        const response = await fetch(API.CORE.GET_BY_STUDENT + studentId);
        const messages = await response.json();
        
        displayMessages(messages);
        messagesMessage.classList.add('hidden');
      } catch (error) {
        showMessage(messagesMessage, 'Error fetching messages: ' + error.message, 'error');
      } finally {
        fetchMessagesBtn.textContent = 'Fetch Messages';
        fetchMessagesBtn.disabled = false;
      }
    }
  
    function displayMessages(messages) {
      messagesTable.innerHTML = '';
      
      if (messages.length === 0) {
        showMessage(messagesMessage, 'No messages found for this student', 'info');
        return;
      }
      
      messages.forEach(message => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${message.id || ''}</td>
          <td>${message.text || ''}</td>
          <td>${message.readed ? 'Yes' : 'No'}</td>
          <td>
            <button class="btn secondary edit-message" data-id="${message.id}">Edit</button>
            <button class="btn danger delete-message" data-id="${message.id}">Delete</button>
          </td>
        `;
        messagesTable.appendChild(row);
        
        // Add event listeners for the buttons
        row.querySelector('.edit-message').addEventListener('click', () => editMessage(message));
        row.querySelector('.delete-message').addEventListener('click', () => deleteMessage(message.id));
      });
    }
  
    function showNewMessageForm() {
      messageFormTitle.textContent = 'New Message';
      messageId.value = '';
      messageStudent.value = messageStudentId.value || '';
      messageText.value = '';
      messageRead.checked = false;
      messageForm.classList.remove('hidden');
    }
  
    function editMessage(message) {
      messageFormTitle.textContent = 'Edit Message';
      messageId.value = message.id;
      messageStudent.value = message.student;
      messageText.value = message.text;
      messageRead.checked = message.readed;
      messageForm.classList.remove('hidden');
    }
  
    function hideMessageForm() {
      messageForm.classList.add('hidden');
    }
  
    async function saveMessage(e) {
      e.preventDefault();
      
      const messageData = {
        text: messageText.value,
        readed: messageRead.checked,
        student: messageStudent.value
      };
      
      const isNewMessage = !messageId.value;
      const url = isNewMessage ? API.CORE.CREATE : API.CORE.UPDATE + messageId.value;
      const method = isNewMessage ? 'POST' : 'PUT';
      
      try {
        const response = await fetch(url, {
          method: method,
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(messageData)
        });
        
        if (response.ok) {
          hideMessageForm();
          showMessage(messagesMessage, `Message ${isNewMessage ? 'created' : 'updated'} successfully`, 'success');
          fetchMessages(); // Refresh the message list
        } else {
          const error = await response.json();
          throw new Error(error.message || 'Failed to save message');
        }
      } catch (error) {
        showMessage(messagesMessage, 'Error saving message: ' + error.message, 'error');
      }
    }
  
    async function deleteMessage(id) {
      if (!confirm('Are you sure you want to delete this message?')) {
        return;
      }
      
      try {
        const response = await fetch(API.CORE.DELETE + id, {
          method: 'DELETE'
        });
        
        if (response.ok) {
          showMessage(messagesMessage, 'Message deleted successfully', 'success');
          fetchMessages(); // Refresh the message list
        } else {
          const error = await response.json();
          throw new Error(error.message || 'Failed to delete message');
        }
      } catch (error) {
        showMessage(messagesMessage, 'Error deleting message: ' + error.message, 'error');
      }
    }
  
    // Check Student Functions
    function initCheckStudent() {
      checkStudentBtn.addEventListener('click', checkStudentExists);
      checkStudentNumber.addEventListener('keyup', function(e) {
        if (e.key === 'Enter') {
          checkStudentExists();
        }
      });
    }
  
    async function checkStudentExists() {
      const studentNum = checkStudentNumber.value.trim();
      
      if (!studentNum) {
        showMessage(checkMessage, 'Please enter a student number', 'error');
        checkResult.classList.remove('hidden');
        return;
      }
      
      checkStudentBtn.textContent = 'Checking...';
      checkStudentBtn.disabled = true;
      
      try {
        const response = await fetch(API.PARSER.CHECK + studentNum);
        const data = await response.json();
        
        checkResult.classList.remove('hidden');
        
        if (data.exists) {
          showMessage(checkMessage, `Student with number ${studentNum} exists in the database.`, 'success');
        } else {
          showMessage(checkMessage, `Student with number ${studentNum} does not exist in the database.`, 'error');
        }
      } catch (error) {
        showMessage(checkMessage, 'Error checking student: ' + error.message, 'error');
      } finally {
        checkStudentBtn.textContent = 'Check Student';
        checkStudentBtn.disabled = false;
      }
    }
  
    // Initialize all components
    function init() {
      // Initialize navigation
      showSection('home');
      
      // Initialize tabs
      initTabs();
      
      // Initialize forms
      initUploadForm();
      initStudentList();
      initMessagesSection();
      initCheckStudent();
    }
  
    // Start the application
    init();
  });