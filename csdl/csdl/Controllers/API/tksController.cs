using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using csdl.Models;

namespace csdl.Controllers.API
{
    public class tksController : ApiController
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: api/tks
        public IQueryable<tk> Gettks()
        {
            return db.tks;
        }
        [HttpGet]
        [Route("api/tk/gettt/{id}")]
        public IHttpActionResult gettt(int id)
        {
            tk tk = db.tks.Find(id);
            if (tk == null)
            {
                return NotFound();
            }

            return Ok(tk);
        }
        // GET: api/tks/5
        [HttpGet]
        [Route("api/tk/kiemtra/{taikhoan}/{mk}")]
        public IHttpActionResult kiemtra(string taikhoan, string mk)
        {
            var tk = db.tks.Where(x => x.taikhoan == taikhoan && x.mk == mk);
            if (!tk.Any())
            {
                return NotFound();
            }

            return Ok(tk);
        }

        [HttpPut]
        [Route("api/tk/Puttk/{id}")]
        public IHttpActionResult Puttk(int id, tk tk)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tk.idtk)
            {
                return BadRequest();
            }

            db.Entry(tk).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!tkExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok(tk);
        }

        [HttpPost]
        [Route("api/tk/Posttk")]
        public IHttpActionResult Posttk(tk tk)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tks.Add(tk);
            db.SaveChanges();

            return Ok(tk);
        }

        [HttpDelete]
        [Route("api/tk/Deletetk/{id}")]
        public IHttpActionResult Deletetk(int id)
        {
            tk tk = db.tks.Find(id);
            if (tk == null)
            {
                return NotFound();
            }

            db.tks.Remove(tk);
            db.SaveChanges();

            return Ok(tk);
        }
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool tkExists(int id)
        {
            return db.tks.Count(e => e.idtk == id) > 0;
        }
    }
}